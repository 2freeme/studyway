package com.base.util.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-8-14 11:43
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DealStudent implements Serializable {
    Student1 student1;
    String name;
}
