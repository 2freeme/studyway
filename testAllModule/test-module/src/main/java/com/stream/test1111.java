package com.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author：
 * @Description：
 * @Date： 2020/12/30 9:51
 */
public class test1111 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        List<String> collect = list.stream().filter(a -> a.equals("2")).collect(Collectors.toList());
        System.out.println(collect.size());
    }
}
