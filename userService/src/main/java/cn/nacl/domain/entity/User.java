package cn.nacl.domain.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
public class User {
    @Id
    @NonNull
    @Column(name = "uid")
    private Long uid;

    @NonNull
    @Column(name = "password")
    private String password;

    @NonNull
    @Column(name = "username")
    private String username;

    @NonNull
    @Column(name = "phone")
    private String phone;

    @NonNull
    @Column(name = "email")
    private String email;

    @NonNull
    @Column(name = "used")
    private Boolean isUsed = true;
}
