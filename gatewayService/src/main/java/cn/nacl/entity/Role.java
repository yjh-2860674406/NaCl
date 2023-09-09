package cn.nacl.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "role")
@Getter
@Setter
@ToString
public class Role implements Serializable {
    @Id
    @NonNull
    @Column(name = "rid")
    private Integer rid;

    @NonNull
    @Column(name = "rname")
    private String rname;
}
