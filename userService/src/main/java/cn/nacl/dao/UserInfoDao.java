package cn.nacl.dao;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import cn.nacl.domain.entity.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDao extends JpaRepository<UserInfo, Long> {
    public UserInfo findByUid (@NonNull Long uid);
}
