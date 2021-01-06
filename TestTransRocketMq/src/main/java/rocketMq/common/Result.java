package rocketMq.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author：
 * @Description：
 * @Date： 2021/1/5 15:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private String code;
    private String msg;
    private List<String> errorList;
    private String traceId;
    private T data;
}
