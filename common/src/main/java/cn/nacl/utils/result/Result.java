package cn.nacl.utils.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class Result <T> {
    private int code;
    private List<T> data;
    private String msg;

    public Result() {
        code = 0;
        data = null;
        msg = "";
    }

    public Result(int code, List<T> data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
}
