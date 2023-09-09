package cn.nacl.service.Impl;

import cn.nacl.entity.Role;
import cn.nacl.entity.UserRole;
import cn.nacl.utils.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("role-service")
public interface RoleClient {
    @GetMapping("/role")
    public Result<Role> getRole (Role role);

    @GetMapping("/user")
    public Result<UserRole> getUserRole (@RequestParam("uid") Long uid, @RequestParam("rid") Integer rid);
}
