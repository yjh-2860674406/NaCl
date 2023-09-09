package cn.nacl.service.Impl;

import cn.nacl.dao.UserInfoDao;
import cn.nacl.domain.entity.UserInfo;
import cn.nacl.service.UserInfoService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo getInfoByUid(@NonNull Long uid) {
        return userInfoDao.findByUid(uid);
    }

    @Override
    public UserInfo save(@NonNull UserInfo userInfo) {
        return userInfoDao.save(userInfo);
    }
}
