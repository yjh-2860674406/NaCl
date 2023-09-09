package cn.nacl.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class Message implements Serializable{
    private static final long serialVersionUID = 1234567;

    @NonNull
    private Long mid;

    @NonNull
    private Long suid;

    private Long ruid;

    private Long rgid;

    @NonNull
    private String msg;

    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp sdate;

    private boolean hadRead = false;
}
