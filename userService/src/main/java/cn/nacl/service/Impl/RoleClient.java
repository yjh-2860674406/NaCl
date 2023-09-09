package cn.nacl.service.Impl;

import cn.nacl.domain.entity.Role;
import cn.nacl.domain.entity.UserRole;
import cn.nacl.utils.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;

@Component
@FeignClient("role-service")
public interface RoleClient {
    @GetMapping("/role")
    public Result<Role> getRole (Role role);

    @GetMapping("/role/user")
    public Result<UserRole> getUserRole (@RequestParam("uid") Long uid, @RequestParam("rid") Integer rid);

    @PostMapping("/role/user")
    public Result<UserRole> addUserRole (@RequestBody UserRole userRole);
}
