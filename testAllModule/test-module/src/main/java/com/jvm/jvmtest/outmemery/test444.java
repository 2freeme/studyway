package com.jvm.jvmtest.outmemery;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-7-2 17:52
 */
public class test444

{
    public static void main(String[] args) {
        JSONObject response = new JSONObject();
        response.put("isSuccess", "Y");
        response.put("responseInfo", "接收成功");
        response.put("data", null);
        JSONObject content = null;
        try {
            try {
                System.out.println(10 / 0);
            } catch (Exception e) {
                throw new Exception();
                //安得错误驳回客户签收数量
            }
            response.put("data", content);
        } catch (Exception e) {
            System.out.println("安得异步通知结果失败"+response.getString("orderNo"));
            response.put("isSuccess","N");
            if (e.getMessage() !=null){
                response.put("responseInfo", "接收失败:" + (e.getMessage().length() > 40 ? e.getMessage().substring(0, 40) : e.getMessage()));
            }else{
                response.put("responseInfo", "接收失败");
            }
        }
        BigDecimal bg = new BigDecimal(0);
        for (int i = 0; i <101 ; i++) {
           bg= bg.add(new BigDecimal( i));
        }
        System.out.println(bg);
        System.out.println(response.toJSONString());

        BigDecimal b = BigDecimal.ZERO;
        for (int i = 0; i <1000 ; i++) {
            b=b.add(new BigDecimal(i));
        }
        System.out.println(b);
    }
}
