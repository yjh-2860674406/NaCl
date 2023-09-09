package cn.nacl.manager;

import cn.nacl.entity.LoginUser;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Component
public class UserAuthenticationManager implements ReactiveAuthenticationManager {
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        if (authentication.isAuthenticated()) return Mono.just(authentication);

        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken)authentication;
        // 转换类型
        LoginUser loginUser = (LoginUser)authenticationToken.getPrincipal();
        // 获取用户信息

        if (!passwordEncoder.matches((String)authenticationToken.getCredentials(), loginUser.getPassword())) {
            return Mono.error(new BadCredentialsException("用户不存在或者密码错误"));
        }

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return Mono.just(authentication);
    }

}
