package cn.nacl.dao;

import cn.nacl.domain.entity.Role;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {
    public Role findByRid (@NonNull Integer rid);
    public Role findByRname (@NonNull String rname);

    public @NonNull List<Role> findAll ();
}
