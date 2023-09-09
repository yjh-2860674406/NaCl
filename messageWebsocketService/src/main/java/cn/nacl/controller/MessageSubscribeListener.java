package cn.nacl.controller;


import cn.nacl.config.RedisValue;
import cn.nacl.utils.websocket.SubscribeListener;
import cn.nacl.utils.websocket.WebSocket;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

@Getter
@Setter
@Slf4j
public class MessageSubscribeListener implements SubscribeListener {
    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        String mid = (String)jdkSerializationRedisSerializer.deserialize(message.getBody());
        String ruid = new String(pattern).substring(7);
        MessageWebSocket.sendMessage(mid, ruid);
    }
}
