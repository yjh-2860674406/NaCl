package cn.nacl.utils.websocket;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public interface SubscribeListener extends MessageListener {
    @Override
    public void onMessage(@NonNull Message message, byte[] pattern);
}
