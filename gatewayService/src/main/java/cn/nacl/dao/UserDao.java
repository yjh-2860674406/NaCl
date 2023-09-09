package cn.nacl.dao;

import cn.nacl.entity.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByUsernameAndUsed (@NonNull String username, boolean used);
}
