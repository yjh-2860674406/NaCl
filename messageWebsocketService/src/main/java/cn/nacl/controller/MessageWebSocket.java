package cn.nacl.controller;

import cn.nacl.config.RedisValue;
import cn.nacl.domain.entity.Message;
import cn.nacl.utils.kafka.KafkaUtils;
import cn.nacl.utils.redis.RedisCache;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Slf4j
@ServerEndpoint("/websocket/{dev}/{uid}")
public class MessageWebSocket {
    private String uid; // 用户唯一描述符
    private String dev; // 登陆设备
    private static final Lock lock = new ReentrantLock(); // 发布消息时的锁
    private static final ArrayList<String> devList = new ArrayList<>(); // 合法设备列表
    private static RedisCache redisCache; // Redis 缓存
    private final KafkaUtils.KafkaStreamClient kafkaClient = KafkaUtils.bulidClient().createKafkaStreamClient("127.0.0.1", 9092, "Message");
    private final MessageSubscribeListener subscribeListener = new MessageSubscribeListener(this);
    private final long secret = -Math.abs(new Random().nextLong());
    private Session session = null;


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
            // 重复登陆，退出上一个登陆的设备
            KafkaUtils.bulidServer().createKafkaStreamServer("127.0.0.1:9092").sendMsg("Message" + uid, devList.indexOf(dev), devList.size(), Long.toString(secret));
            this.uid = uid;
            this.dev = dev;
            this.session = session;
            kafkaClient.subscribe("Message" + uid, devList.indexOf(dev));
            subscribeListener.setKafkaConsumer(kafkaClient.getKafkaConsumer());
            subscribeListener.call();
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
            session.close();
            // 关闭session
            kafkaClient.close();
            subscribeListener.setTag(false);
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


    public void sendMessage (String mid) {
        long secret = Long.parseLong(mid);
        if (secret < 0 && secret != this.secret) {
            onClose();
            return;
        }
        Message message = redisCache.getCacheMapValue(RedisValue.message + uid, mid);
        if (message == null) return;
        // 根据 mid 获取消息
        message.setHadRead(true);
        // 设置消息为已读状态
        redisCache.setCacheMapValue(RedisValue.message + uid, mid, message);
        // 将已读状态的消息存回缓存
        if (session != null && session.isOpen()) {
            try {
                lock.lock();
                // 上锁
                log.info("Uid为:" + uid + "的用户将收到消息:" + message);
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
