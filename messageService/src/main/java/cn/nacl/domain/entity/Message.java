package cn.nacl.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "message")
@Getter
@Setter
@ToString
public class Message implements Serializable, Cloneable {
    private static final long serialVersionUID = 1234567;

    @Id
    @Column(name = "mid")
    @NonNull
    private Long mid;

    @Column(name = "suid")
    @NonNull
    private Long suid;

    @Column(name = "ruid")
    private Long ruid;

    @Column(name = "rgid")
    private Long rgid;

    @Column(name = "msg")
    @NonNull
    private String msg;

    @Column(name = "sdate")
    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp sdate;

    @Column(name = "hadread")
    private boolean hadRead = false;

}
