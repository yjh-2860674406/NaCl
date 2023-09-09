package cn.nacl.controller;

import cn.nacl.domain.entity.Role;
import cn.nacl.domain.entity.UserRole;
import cn.nacl.service.RoleService;
import cn.nacl.service.UserRoleService;
import cn.nacl.utils.result.Result;
import cn.nacl.utils.result.ResultCode;
import cn.nacl.utils.result.ResultFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;

    @GetMapping("/")
    public Result<Role> getRole (RoleVO roleVO) {
        List<Role> data = new ArrayList<>();
        Role role = new Role();
        if (roleVO.getRid() != null) role = roleService.getRoleByRid(roleVO.getRid());
        else if (roleVO.getRname() != null) role = roleService.getRoleByRname(roleVO.getRname());
        else return ResultFactory.getResult(ResultCode.fail, null, "请给出查询条件");
        if (role == null) return ResultFactory.getResult(ResultCode.fail, null, "没有该数据");
        data.add(role);
        return ResultFactory.getResult(ResultCode.success, data, "获取成功");
    }

    @GetMapping("/user")
    public Result<UserRole> getUserRole (@RequestParam("uid") Long uid, @RequestParam("rid") Integer rid) {
        List<UserRole> data = null;
        if (uid != null) data = userRoleService.getUserRolesByUid(uid);
        else if (rid != null) data = userRoleService.getUserRolesByRid(rid);
        else return ResultFactory.getResult(ResultCode.fail, null, "请给出查询条件");
        return ResultFactory.getResult(ResultCode.success, data, "获取数据成功");
    }

    @PostMapping("/user")
    public Result<UserRole> addUserRole (@RequestBody UserRoleVO userRoleVO){
        UserRole userRole = new UserRole();
        userRole.setUid(userRoleVO.getUid());
        Role role = roleService.getRoleByRname(userRoleVO.getRid().getRname());
        if (role == null) return ResultFactory.getResult(ResultCode.fail, null, "没有该权限类别");
        userRole.setRid(role.getRid());
        userRoleService.addUserRole(userRole);
        return ResultFactory.getResult(ResultCode.success, null, "添加成功");
    }
}

@Getter
@Setter
class RoleVO {
    private Integer rid;
    private String rname;
}

@Getter
@Setter
class UserRoleVO {
    private Long uid;
    private RoleVO rid;
}