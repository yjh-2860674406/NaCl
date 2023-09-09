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
@Table(name = "user_info")
@Getter
@Setter
@ToString
public class UserInfo {
    @Id
    @NonNull
    @Column(name = "uid")
    private Long uid;

    @NonNull
    @Column(name = "nickname")
    private String nickname;

    @NonNull
    @Column(name = "age")
    private Integer age;

    @NonNull
    @Column(name = "sex")
    private Integer sex;

    @NonNull
    @Column(name = "signature")
    private String signature;
}
