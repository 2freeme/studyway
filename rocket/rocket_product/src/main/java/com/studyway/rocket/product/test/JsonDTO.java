package com.studyway.rocket.product.test;

import lombok.Data;

import java.util.List;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020/10/16 23:45
 */
@Data
public class JsonDTO {
    private String q;
    private String w;
    private List<JonArray> jsonArray;

    @Data
    class JonArray{
        private String aa;
        private String cc;
    }
}
