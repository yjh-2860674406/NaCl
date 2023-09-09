package cn.nacl.service.Impl;

import cn.nacl.dao.RoleDao;
import cn.nacl.domain.entity.Role;
import cn.nacl.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;

    @Override
    public Role getRoleByRid(Integer rid) {
        return roleDao.findByRid(rid);
    }

    @Override
    public Role getRoleByRname(String rname) {
        return roleDao.findByRname(rname);
    }
}
