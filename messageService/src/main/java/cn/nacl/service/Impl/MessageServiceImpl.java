package cn.nacl.service.Impl;

import cn.nacl.config.RedisValue;
import cn.nacl.dao.MessageDao;
import cn.nacl.domain.adapter.MessageAdapter;
import cn.nacl.domain.dto.MessageDTO;
import cn.nacl.domain.entity.GRVO;
import cn.nacl.domain.entity.Message;
import cn.nacl.service.MessageService;
import cn.nacl.service.client.GroupClient;
import cn.nacl.utils.redis.RedisCache;
import cn.nacl.utils.snow.SnowFlakeUtil;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageDao messageDao;

    @Resource
    private MessageAdapter messageAdapter;

    @Resource
    private RedisCache redisCache;



    /**
     * 将消息保存至 redis 缓存中
     *
     * @param messageDTO 缓存的消息数据
     * @return 具有 mid 消息
     */
    @Override
    public MessageDTO saveMessageOnCache (MessageDTO messageDTO, List<GRVO> grvoList) {
        Message message = messageAdapter.convertMessageDTO2Message(messageDTO);
        // DTO -> BO
        SnowFlakeUtil snowFlakeUtil = null;
        if (grvoList == null) snowFlakeUtil = new SnowFlakeUtil(message.getSuid(), message.getRuid());
        else snowFlakeUtil = new SnowFlakeUtil(message.getSuid(), message.getRgid());
        message.setMid(snowFlakeUtil.getNextId());
        // 通过雪花算法计算 mid -> 通过发送者和接收者（用户或群）计算
        if (grvoList == null) {
            // user -> user
            redisCache.setCacheMapValue(RedisValue.message + message.getRuid().toString(), message.getMid().toString(), message);
        }
        else {
            // user -> group
            if (!grvoList.isEmpty()) {
                // 当群不空
                for (GRVO grvo : grvoList) redisCache.setCacheMapValue(RedisValue.message + grvo.getUid().toString(), message.getMid().toString(), message);
                // 逐个将消息添加进对应的接收用户缓存中
            }
        }
        // 添加入缓存 map 中，hash 的 key 为接收者的 uid 或 gid ，map 的 key 为发送者的uid
        return messageAdapter.convertMessage2MessageDTO(message);
        // 返回 DTO
    }

    /***
     * 根据 Redis 的发布订阅机制，通知对应已登录的用户服务器发送消息
     * @param messageDTO 消息
     * @param grvoList 群发用户列表
     */
    @Override
    public void sendMessage (MessageDTO messageDTO, List<GRVO> grvoList) {
        if (grvoList == null) redisCache.convertAndSend(RedisValue.channel + messageDTO.getRuid().toString(), messageDTO.getMid().toString());
        else for (GRVO grvo : grvoList) redisCache.convertAndSend(RedisValue.channel + grvo.getUid().toString(), messageDTO.getMid().toString());
    }

    /**
     * 将消息设置为已读并存入数据库，减少 redis 的负载
     *
     * @param messageDTO 要设置已读和保存的消息数据
     */
    @Override
    public boolean setReadOnCacheAndSaveOnDB (MessageDTO messageDTO) {
        Message message = redisCache.getCacheMapValue(RedisValue.message + messageDTO.getRuid().toString(), messageDTO.getMid().toString());
        // 根据 ruid 和 mid 获取消息数据
        if (message == null) return false;
        // 如果不存在，则可能已经被其他服务器删除或数据被存入数据库中了，所以返回 false，让控制器自行查询数据库是否还存在未读数据
        message.setHadRead(true);
        // 将其标记为已读
        messageDao.save(message);
        // 数据库保存该消息数据
        redisCache.delCacheMapValue(RedisValue.message + messageDTO.getRuid().toString(), messageDTO.getSuid().toString());
        // redis 删除其缓存
        return true;
    }

    /**
     * 获取缓存中未读消息的数据
     *
     * @param messageDTO 要获取未读消息
     * @return 未读消息列表
     */
    @Override
    public List<MessageDTO> getUnReadOnCache (MessageDTO messageDTO) {
        Map<String, Message> messages = redisCache.getCacheMap(RedisValue.message + messageDTO.getRuid().toString());
        // 从缓存中获取未读数据
        List<MessageDTO> unReadMessageDTOs = new ArrayList<>();
        for (Message message : messages.values()) {
            // BO -> DTO
            if (!message.isHadRead()) unReadMessageDTOs.add(messageAdapter.convertMessage2MessageDTO(message));
        }
        return unReadMessageDTOs;
    }

    /***
     * 判断数据库中是否仍有未读消息
     * @param messageDTO 获取未读消息的用户uid
     * @return ture or false
     */
    @Override
    public boolean detectUnReadOnDB(MessageDTO messageDTO) {
        redisCache.setCacheMapValue(RedisValue.hasUnReadOnDB, messageDTO.getRuid().toString(), true);
        // TODO
        // 还未确定 Redis 的持久化和与 Mysql 的连接，默认数据库中存在已读数据
        return redisCache.getCacheMapValue(RedisValue.hasUnReadOnDB, messageDTO.getRuid().toString());
    }

    /***
     * 将未读消息设置为已读并保存到数据库中
     * @param messageDTO 未读消息
     */
    @Override
    public void setReadAndSaveOnDB(MessageDTO messageDTO) {
        Message message = messageAdapter.convertMessageDTO2Message(messageDTO);
        message.setHadRead(true);
        messageDao.save(message);
    }

    /***
     * 获取数据库中的未读消息
     * @param messageDTO 需要获取未读消息的接收用户uid
     * @return 未读消息
     */
    @Override
    public List<MessageDTO> getUnReadOnDB(MessageDTO messageDTO) {
        List<Message> unReadMessages = messageDao.findAllByRuidAndHadReadOrderBySdate(messageDTO.getRuid(), false);
        // 查找对应ruid和未读的消息
        List<MessageDTO> unReadMessageDTOs = new ArrayList<>();
        for (Message unReadMessage : unReadMessages) {
            // BO -> DTO
            unReadMessageDTOs.add(messageAdapter.convertMessage2MessageDTO(unReadMessage));
        }
        return unReadMessageDTOs;
    }
}
