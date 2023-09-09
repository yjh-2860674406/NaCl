package cn.nacl.domain.vo;

import cn.nacl.domain.entity.UserInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVO {
    private Long uid;

    private String password;

    private String username;

    private String phone;

    private String email;

    private UserInfo userInfo;
}
