package com.optional;

import lombok.Data;

/**
 * @Author：
 * @Description：
 * @Date： 2020-10-17 9:57
 */

@Data
public class Result <T> {
        private java.lang.String code;
        private java.lang.String msg;
        private java.util.List<java.lang.String> errorList;
        private T data;
}
