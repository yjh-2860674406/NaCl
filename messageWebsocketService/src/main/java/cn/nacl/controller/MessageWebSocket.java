package cn.nacl.controller;

import cn.nacl.config.RedisValue;
import cn.nacl.domain.entity.Message;
import cn.nacl.utils.redis.RedisCache;
import cn.nacl.utils.websocket.WebSocket;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Slf4j
@ServerEndpoint("/websocket/{dev}/{uid}")
public class MessageWebSocket extends WebSocket {
    private String uid; // 用户唯一描述符
    private String dev; // 登陆设备
    private MessageSubscribeListener subscribeListener = null; // 监听器
    private static final Lock lock = new ReentrantLock(); // 发布消息时的锁
    private static final ArrayList<String> devList = new ArrayList<>(); // 合法设备列表
    private static final ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<>(); // 连接池
    private static RedisCache redisCache; // Redis 缓存
    private static RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer(); // 监听器容器

    static {
        devList.add("android");
        devList.add("ios");
        devList.add("windows");
        devList.add("linux");
        devList.add("mac");
    }

    @Autowired
    public void setRedisCache(RedisCache redisCache) {
        MessageWebSocket.redisCache = redisCache;
    }

    @Autowired
    public void setRedisMessageListenerContainer (RedisMessageListenerContainer redisMessageListenerContainer) {
        MessageWebSocket.redisMessageListenerContainer = redisMessageListenerContainer;
    }

    @OnOpen
    public void onOpen (Session session, @PathParam("dev") String dev, @PathParam("uid") String uid) {
        try {
            log.info("Uid为: " + uid + "的用户尝试在设备: " + dev + " 上连接");
            // 打印日志
            if (!devList.contains(dev)) {
                // 非法设备则取消连接
                log.info("连接结果: 取消，原因: 非法设备");
                session.close(new CloseReason(CloseReason.CloseCodes.getCloseCode(1000), "非法设备请求"));
            }
            // TODO 重复登陆，退出上一个登陆的设备，还需要再加一个监听器
            sessionPool.put(uid + dev, session);
            // 存入池中
            subscribeListener = new MessageSubscribeListener();
            // 监听器
            ChannelTopic channelTopic = new ChannelTopic(RedisValue.channel + uid);
            // 要订阅的频道
            redisMessageListenerContainer.addMessageListener(subscribeListener, channelTopic);
            // 将监听器和频道存入对应容器中
            this.uid = uid;
            this.dev = dev;
            // 设置 该对象的 uid 和 dev
            log.info("连接结果: 成功");
            // 打印日志结果
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose() {
        try {
            log.info("Uid为:" + uid + "的用户取消在设备" + dev + "上的连接");
            // 打印日志
            sessionPool.get(uid + dev).close();
            // 关闭session
            sessionPool.remove(uid + dev);
            // 从连接池中移去
            redisMessageListenerContainer.removeMessageListener(subscribeListener);
            // 从监听器容器中移去
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage (String message) {
        // TODO 用于接收数据，如视频、音乐、文件等
        log.info("Uid为:" + uid + "的用户通过连接发送消息:" + message);
    }

    @OnError
    public void onError (Session session, Throwable error) {
        log.error(error.getMessage());
    }

    public static void sendMessage (String mid, String ruid) {
        Message message = redisCache.getCacheMapValue(RedisValue.message + ruid, mid);
        // 根据 mid 获取消息
        message.setHadRead(true);
        // 设置消息为已读状态
        redisCache.setCacheMapValue(RedisValue.message + ruid, mid, message);
        // 将已读状态的消息存回缓存
        for (String dev : devList) {
            Session session = sessionPool.get(ruid + dev);
            // 查询各个设备，是否存在要接收的用户
            if (session != null && session.isOpen()) {
                try {
                    lock.lock();
                    // 上锁
                    log.info("Uid为:" + ruid + "的用户将收到消息:" + message);
                    // 打印日志
                    session.getAsyncRemote().sendText(JSONObject.toJSONString(message));
                    // 异步发送消息
                    lock.unlock();
                    // 解锁
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
