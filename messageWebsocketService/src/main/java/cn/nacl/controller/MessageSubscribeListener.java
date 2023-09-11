package cn.nacl.controller;


import cn.nacl.utils.kafka.KafkaUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.time.Duration;
import java.util.concurrent.Callable;

@Getter
@Setter
@Slf4j
public class MessageSubscribeListener implements Callable {
    private MessageWebSocket webSocket = null;
    private KafkaConsumer<String, String> kafkaConsumer;
    private boolean tag = true;

    public MessageSubscribeListener (MessageWebSocket webSocket) {
        this.webSocket = webSocket;
    }

    public void pollMsg() {
        while (tag) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                String mid = record.value();
                webSocket.sendMessage(mid);
            }
        }
    }

    @Override
    public Object call() throws Exception {
        pollMsg();
        return null;
    }
}
