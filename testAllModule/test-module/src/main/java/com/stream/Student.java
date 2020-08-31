package com.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author： Dingpengfei
 * @Description：  测试序列的使用
 * @Date： 2020-5-12 9:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student implements Serializable {
    private String name;
    private Integer age;

}
