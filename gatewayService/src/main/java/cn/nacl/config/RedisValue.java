package cn.nacl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisValue {
    public static String prefix;

    public static String loginUser = "loginUser";

    @Value("${spring.redis.prefix}")
    public void setPrefix (String prefix) {
        RedisValue.prefix = prefix;
    }
}
