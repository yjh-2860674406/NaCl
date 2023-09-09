package cn.nacl.dao;

import cn.nacl.domain.entity.GR;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
public interface GRDao extends JpaRepository<GR, Long> {
    @Override
    <S extends GR> @NonNull S save(@NonNull S entity);

    @Modifying
    @Transactional
    void removeByGidAndUid (@NonNull Long gid, @NonNull Long uid);

    List<GR> findAllByGid (@NonNull Long gid);

    GR findByGidAndUid (@NonNull Long gid, @NonNull Long uid);
}
