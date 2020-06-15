package com.mo.empty;

/**
 * @Author： Dingpengfei
 * @Description： 用作测试
 * @Date： 2020-5-22 9:54
 */
public class EmptyTest {
    public static void main(String[] args) {
        //字符串的操作比较慢
        String cod1e =(Math.random()+"").substring(2,10);
        //比较快 因为都是字符的运算
        String code = String.valueOf((int)((Math.random()*9+1)*Math.pow(10,5)));
        System.out.println(Math.pow(10,5));
        System.out.println(code);
    }

}
