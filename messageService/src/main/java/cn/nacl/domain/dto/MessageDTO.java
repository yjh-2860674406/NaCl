package cn.nacl.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class MessageDTO {

    private Long mid;

    private Long suid;

    private Long ruid;

    private Long rgid;

    private String msg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp sdate;
}
