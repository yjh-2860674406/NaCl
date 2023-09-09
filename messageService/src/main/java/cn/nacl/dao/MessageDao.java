package cn.nacl.dao;

import cn.nacl.domain.entity.Message;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDao extends JpaRepository<Message, Long> {
    @Override
    <S extends Message> @NonNull S save(@NonNull S entity);

    List<Message> findAllByRuidAndHadReadOrderBySdate (Long ruid, boolean hadRead);
}
