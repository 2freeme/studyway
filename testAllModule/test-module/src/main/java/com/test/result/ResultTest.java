package com.test.result;

import lombok.Data;

/**
 * @Author：
 * @Description：
 * @Date： 2020-11-8 23:28
 */
@Data
public class ResultTest<T> {
    private java.lang.String code;
    private T data;

}
