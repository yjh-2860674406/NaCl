package cn.nacl.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
public class User implements Serializable {
    @Id
    @NonNull
    @Column(name = "uid")
    private Long uid;

    @NonNull
    @Column(name = "username")
    private String username;

    @NonNull
    @Column(name = "password")
    private String password;

    @NonNull
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "uid")
    private List<UserRole> roles;

    @NonNull
    @Column(name = "used")
    private boolean used;
}
