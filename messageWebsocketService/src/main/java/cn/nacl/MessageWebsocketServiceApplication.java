package cn.nacl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MessageWebsocketServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(MessageWebsocketServiceApplication.class);
    }
}
