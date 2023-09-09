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


    @Column(name = "nickname")
    private String nickname;


    @Column(name = "age")
    private Integer age;


    @Column(name = "sex")
    private Integer sex;


    @Column(name = "signature")
    private String signature;
}
