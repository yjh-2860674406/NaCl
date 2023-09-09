package cn.nacl.service;


import cn.nacl.domain.entity.Role;
import cn.nacl.domain.entity.UserRole;

import java.util.List;

public interface UserRoleService {
    public List<UserRole> getUserRolesByUid (Long uid);
    public List<UserRole> getUserRolesByRid (Integer rid);

    public void addUserRole (UserRole userRole);
}
