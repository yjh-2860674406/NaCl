package cn.nacl.manager;

import cn.nacl.config.RedisValue;
import cn.nacl.entity.LoginUser;
import cn.nacl.utils.jwt.JwtUtil;
import cn.nacl.utils.redis.RedisCache;
import com.alibaba.nacos.api.utils.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Component
public class UserSecurityContextRepository implements ServerSecurityContextRepository {

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private RedisCache redisCache;

    @Resource
    private UserAuthenticationManager userAuthenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        // 获取请求头
        String token = headers.getFirst("token");
        // 获取请求头中的token
        if (StringUtils.isBlank(token)) {
            // 如果空，则放行
            return Mono.empty();
        }
        String username = jwtUtil.getUsernameFromToken(token);
        // 从token中获取username
        LoginUser loginUser = redisCache.getCacheMapValue(RedisValue.prefix + RedisValue.loginUser, username);
        // 根据username从缓存中获取用户信息loginUser
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginUser, loginUser.getPassword(), loginUser.getAuthorities());
        // 转成token对象
        return userAuthenticationManager.authenticate(authToken).map(SecurityContextImpl::new);
    }
}
