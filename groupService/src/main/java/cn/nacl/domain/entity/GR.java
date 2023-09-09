package cn.nacl.domain.entity;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.C;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "group_relation")
@Getter
@Setter
@ToString
public class GR {
    @Id
    @NonNull
    @Column(name = "grid")
    private Long grid;

    @NonNull
    @Column(name = "gid")
    private Long gid;

    @NonNull
    @Column(name = "uid")
    private Long uid;
}
