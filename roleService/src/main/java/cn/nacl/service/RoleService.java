package cn.nacl.service;

import cn.nacl.domain.entity.Role;

import java.util.List;

public interface RoleService {
    public Role getRoleByRid (Integer rid);
    public Role getRoleByRname (String rname);
}
