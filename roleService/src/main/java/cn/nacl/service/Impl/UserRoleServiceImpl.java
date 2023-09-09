package cn.nacl.service.Impl;

import cn.nacl.dao.UserRoleDao;
import cn.nacl.domain.entity.Role;
import cn.nacl.domain.entity.UserRole;
import cn.nacl.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Resource
    private UserRoleDao userRoleDao;

    @Override
    public List<UserRole> getUserRolesByUid(Long uid) {
        return userRoleDao.findByUid(uid);
    }

    @Override
    public List<UserRole> getUserRolesByRid(Integer rid) {
        return userRoleDao.findByRid(rid);
    }

    @Override
    public void addUserRole(UserRole userRole) {
        userRoleDao.save(userRole);
    }
}
