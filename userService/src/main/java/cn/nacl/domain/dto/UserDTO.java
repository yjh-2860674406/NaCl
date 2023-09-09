package cn.nacl.domain.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;

@Getter
@Setter
@ToString
public class UserDTO {
    private Long uid;

    private String password;

    private String username;

    private String phone;

    private String email;

    public void calculatePassWord () {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }
}
