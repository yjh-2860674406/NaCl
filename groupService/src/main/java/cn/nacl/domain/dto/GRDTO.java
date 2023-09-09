package cn.nacl.domain.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.DigestUtils;

@Getter
@Setter
@ToString
public class GRDTO {
    private Long grid;

    private Long gid;

    private Long uid;
}
