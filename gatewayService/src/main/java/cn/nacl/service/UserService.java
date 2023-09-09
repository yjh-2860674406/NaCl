package cn.nacl.service;

import cn.nacl.entity.LoginUser;
import cn.nacl.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public interface UserService {
    public User getUserByUserName (String username);

    public String login (User user);

    public void loginOut ();
}
