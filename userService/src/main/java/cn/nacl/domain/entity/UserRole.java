package cn.nacl.domain.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_role")
@Getter
@Setter
@ToString
@IdClass(UserRoleId.class)
public class UserRole implements Serializable {
    @Id
    @NonNull
    @Column(name = "uid")
    private Long uid;

    @Id
    @ManyToOne
    @JoinColumn(name = "rid")
    private Role rid;
}

@Getter
@Setter
@ToString
class UserRoleId implements Serializable {
    private Long uid;
    private Role rid;
}
