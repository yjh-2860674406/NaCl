package cn.nacl.config;

import org.springframework.beans.factory.annotation.Value;

public class RedisValue {
    public static String prefix = "";

    public static String message = prefix + "message";

    public static String channel = prefix + "channel";

    public static String hasUnReadOnDB = prefix + "hasUnReadOnDB";

    @Value("${spring.redis.prefix}")
    public void setPrefix (String prefix) {
        RedisValue.prefix = prefix;
    }
}
