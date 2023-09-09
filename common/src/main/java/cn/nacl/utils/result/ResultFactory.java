package cn.nacl.utils.result;

import java.util.List;

public class ResultFactory {
    public static <T> Result<T> getResult (int code, List<T> data, String msg) {
        return new Result<>(code, data, msg);
    }
}
