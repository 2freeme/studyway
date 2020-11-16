package com.teststatic;

/**
 * @Author：
 * @Description：
 * @Date： 2020-11-8 11:37
 */
public class TestStatTest {
    public static void main(String[] args) {

        TcPoSignVo.SignItem item = new TcPoSignVo.SignItem();
        item.setItemCode("ceshi1111111111111=========");
        TcPoSignVo.SignItem item2 = new TcPoSignVo.SignItem();
        item2.setItemCode("2222222222222222222=========");
        System.out.println(item==item2);
        System.out.println(item.hashCode());
        System.out.println(item2.hashCode());
        System.out.println(item.toString());
        System.out.println(item2.toString());
        System.out.println("B"=="B");

    }

}
