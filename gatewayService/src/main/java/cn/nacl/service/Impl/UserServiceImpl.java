package cn.nacl.service.Impl;

import cn.nacl.config.RedisValue;
import cn.nacl.dao.UserDao;
import cn.nacl.entity.LoginUser;
import cn.nacl.entity.User;
import cn.nacl.manager.UserAuthenticationManager;
import cn.nacl.service.UserService;
import cn.nacl.utils.jwt.JwtUtil;
import cn.nacl.utils.redis.RedisCache;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Resource
    private RedisCache redisCache;

    @Resource
    private UserAuthenticationManager userAuthenticationManager;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private UserDao userDao;

    @Override
    public User getUserByUserName (String username) {
        return userDao.findByUsernameAndUsed(username, true);
    }

    /***
     * 请求登陆服务
     * @param user 用户登陆信息
     * @return token
     */
    @Override
    public String login (User user) {
        String username = user.getUsername();
        LoginUser loginUser = new LoginUser(userDao.findByUsernameAndUsed(username, true));
        Authentication authentication = userAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser, user.getPassword())).block();
        // 将用户认证信息和密码和授权信息封装起来
        String token = "";
        if (authentication == null) throw new RuntimeException("登陆失败");
        else {
            // 如果认证成功
            token = jwtUtil.createToken(username);
            // 生成token
            redisCache.setCacheMapValue(RedisValue.prefix + RedisValue.loginUser, username, loginUser);
            // 缓存用户认证信息
        }
        return token;
    }

    /***
     * 请求登出服务
     */
    @Override
    public void loginOut () {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser)usernamePasswordAuthenticationToken.getPrincipal();
        String username = loginUser.getUsername();
        redisCache.delCacheMapValue("loginUser", username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new LoginUser(userDao.findByUsernameAndUsed(username, true));
    }
}
