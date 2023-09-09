package cn.nacl.utils.websocket;

import cn.nacl.utils.redis.RedisCache;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Getter
@Setter
@ToString
@Component
public abstract class WebSocket {

    @OnOpen
    public abstract void onOpen(Session session, String dev, String uid);

    @OnClose
    public abstract void onClose();

    @OnMessage
    public abstract void onMessage (String message);

    @OnError
    public abstract void onError (Session session, Throwable error);
}
