package cn.nacl.dao;

import cn.nacl.domain.entity.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    public User findByUsernameAndPassword (@NonNull String username, @NonNull String password);

    public User findByUid (@NonNull Long uid);

    public User findByUsername (@NonNull String username);

    public User findByPhone (@NonNull String phone);

    public User findByEmail (@NonNull String email);

    @Override
    <S extends User> @NonNull S save(@NonNull S entity);
}
