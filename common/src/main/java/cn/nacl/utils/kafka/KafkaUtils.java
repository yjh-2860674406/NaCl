package cn.nacl.utils.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.internals.Topic;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;


/**
 * @Description kafka工具类，提供消息发送与监听
 * @url https://blog.csdn.net/qq_33460264/article/details/120198173
 * @Author chenhui
 */
@Component
@Slf4j
public class KafkaUtils {

    /**
     * 获取实始化KafkaStreamServer对象
     *
     * @return
     */
    public static KafkaStreamServer bulidServer() {
        return new KafkaStreamServer();
    }

    /**
     * 获取实始化KafkaStreamClient对象
     *
     * @return
     */
    public static KafkaStreamClient bulidClient() {
        return new KafkaStreamClient();
    }

    private static AdminClient adminClient;

    public static class KafkaStreamServer {
        KafkaProducer<String, String> kafkaProducer = null;

        AdminClient adminClient = null;

        private KafkaStreamServer() {
        }

        /**
         * 创建配置属性
         *
         * @param bootstrapServers kafka地址，多个地址用逗号分割
         * @return
         */
        public KafkaStreamServer createKafkaStreamServer(String bootstrapServers) {
            if (kafkaProducer != null) {
                return this;
            }
            Properties properties = new Properties();
            // kafka地址，多个地址用逗号分割
            properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            adminClient = AdminClient.create(properties);
            kafkaProducer = new KafkaProducer<>(properties);
            return this;
        }

        /**
         * 向kafka服务发送生产者消息
         *
         * @param topic
         * @param msg
         * @return
         */
        public Future<RecordMetadata> sendMsg(String topic, int partition, int initNum, String msg) {
            try {
                if (!adminClient.listTopics().names().get().contains(topic)) adminClient.createTopics(List.of(new NewTopic(topic, initNum, (short)1))).all().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, partition, "key", msg);
            return kafkaProducer.send(record);
        }

        /**
         * 关闭kafka连接
         */
        public void close() {
            if (kafkaProducer != null) {
                kafkaProducer.flush();
                kafkaProducer.close();
                kafkaProducer = null;
            }
        }
    }

    public static class KafkaStreamClient {
        KafkaConsumer<String, String> kafkaConsumer = null;

        private KafkaStreamClient() {
        }

        /**
         * 配置属性,创建消费者
         *
         * @param host
         * @param port
         * @return
         */
        public KafkaStreamClient createKafkaStreamClient(String host, int port, String groupId) {
            String bootstrapServers = String.format("%s:%d", host, port);
            if (kafkaConsumer != null) {
                return this;
            }
            Properties properties = new Properties();
            // kafka地址，多个地址用逗号分割
            properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            kafkaConsumer = new KafkaConsumer<String, String>(properties);
            return this;
        }

        /**
         * 客户端消费者拉取消息，并通过回调HeaderInterface实现类传递消息
         *
         * @param topic
         */
        public void subscribe(String topic, Integer partition) {
            if (partition == null) {
                kafkaConsumer.subscribe(Collections.singletonList(topic));
            } else {
                TopicPartition p = new TopicPartition(topic, partition);
                kafkaConsumer.assign(List.of(p));
            }
        }

        public KafkaConsumer<String, String> getKafkaConsumer() {
            return kafkaConsumer;
        }

        /**
         * 关闭kafka连接
         */
        public void close() {
            if (kafkaConsumer != null) {
                kafkaConsumer.close();
                kafkaConsumer = null;
            }
        }
    }
}