package com.forOrder.conbine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-9-15 19:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Header {
    private String  sourceSys ;   //来源系统-cms
    private String  sourceSubsystem ;   //系统来源,ym-易买,dp-大屏,yjy-研究院,xcx-小程序
    private String  userCode ;   //用户编码
    private String  userName ;   //用户姓名
}
