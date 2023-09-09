package cn.nacl.domain.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "relation")
@Getter
@Setter
@ToString
public class Relation {
    @Id
    @NonNull
    @Column(name = "rid")
    private Long rid;

    @NonNull
    @Column(name = "uid1")
    private Long uid1;

    @NonNull
    @Column(name = "uid2")
    private Long uid2;
}
