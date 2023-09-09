package cn.nacl.dao;

import cn.nacl.domain.entity.Relation;
import cn.nacl.domain.entity.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationDao extends JpaRepository<Relation, Long> {
    Relation findByUid1AndUid2 (@NonNull Long uid1, @NonNull Long uid2);
    List<Relation> findAllByUid1 (@NonNull Long uid1);
}
