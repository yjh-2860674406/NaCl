package cn.nacl.config;


import cn.nacl.manager.UserSecurityContextRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.annotation.Resource;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig{
    @Resource
    private UserSecurityContextRepository userSecurityContextRepository;

    @Bean
    public SecurityWebFilterChain securityFilterChain (ServerHttpSecurity http) {
        http
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .securityContextRepository(userSecurityContextRepository)
                .authorizeExchange()
                .pathMatchers("/login").permitAll()
                .pathMatchers("/user/register").permitAll()
                .anyExchange().hasAuthority("user");
        return http.build();
    }
}
