package cn.nacl.dao;

import cn.nacl.domain.entity.Role;
import cn.nacl.domain.entity.UserRole;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleDao extends JpaRepository<UserRole, Integer> {
    public List<UserRole> findByUid (@NonNull Long uid);
    public List<UserRole> findByRid (@NonNull Integer rid);

    @Override
    <S extends UserRole> @NonNull S save(@NonNull S entity);
}
