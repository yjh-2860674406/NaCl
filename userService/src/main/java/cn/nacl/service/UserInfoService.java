package cn.nacl.service;

import cn.nacl.domain.entity.UserInfo;
import lombok.NonNull;

public interface UserInfoService {
    public UserInfo getInfoByUid (@NonNull Long uid);

    public UserInfo save (@NonNull UserInfo userInfo);
}
