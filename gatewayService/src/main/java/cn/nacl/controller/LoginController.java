package cn.nacl.controller;

import cn.nacl.entity.User;
import cn.nacl.service.Impl.UserServiceImpl;
import cn.nacl.service.UserService;
import cn.nacl.utils.result.Result;
import cn.nacl.utils.result.ResultCode;
import cn.nacl.utils.result.ResultFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LoginController {

    @Resource
    private UserService userService;

    @GetMapping("/login")
    public Result<String> login (@RequestBody User user) {
        String token = userService.login(user);
        List<String> data = new ArrayList<>();
        data.add(token);
        return ResultFactory.getResult(ResultCode.success, data, "登陆成功");
    }

    @GetMapping("/logout")
    public Result<String> logout () {
        userService.loginOut();
        return ResultFactory.getResult(ResultCode.success, null, "注销成功");
    }
}
