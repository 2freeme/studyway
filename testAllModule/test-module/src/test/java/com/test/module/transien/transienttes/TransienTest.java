package com.test.module.transien.transienttes;

import com.alibaba.fastjson.JSONObject;
import com.test.module.transien.transienttes.entity.Student;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * @Author： Dingpengfei
 * @Description： 测试 Transien ：
 * Transien  主要做的就是忽略属性序列化的作用,可以忽视对象的属性的序列化
 * 感觉可以脱敏
 * @Date： 2020-5-12 9:51
 */
public class TransienTest {

    @Test
    public void testTransien1() {
        for (int i = 0; i < 10; i++) {
            long l = System.nanoTime();  //这个值的话就是系统的值，不确定是怎么出来的，但是影响不大
            long l1 = System.currentTimeMillis();
            Student student = new Student("aaa", "bbbb",null);
            String s = JSONObject.toJSONString(student);
            Student student1 = JSONObject.parseObject(s, Student.class);
            //注：针对于序列化，如果是转化为序列化 然后又转化为对象的话，里面的空值到后面再次转化的话就会为空
            System.out.println(student1); //Student{name='null', grade='bbbb', age='null'}

            System.out.println(s);          //{"grade":"bbbb"}
            System.out.println(student);    //Student{name='aaa', grade='bbbb', age='null'}
            System.out.println("=====================");
            System.out.println(System.nanoTime() - l);
            System.out.println(System.currentTimeMillis() - l1);
            System.out.println(System.nanoTime());
        }
    }

    /**
     * 用作测试
     * System.setOut
     */
    @Test
    public void test2() {
        try {
            PrintStream out = System.out;
            PrintStream ps = new PrintStream("./log.txt");

            System.setOut(ps);
            int age = 11;
            System.out.println("年龄变量成功定义，初始值为11");
            String sex = "女";
            System.out.println("年龄变量成功定义，初始值为女");
            // 整合2个变量
            String info = "这是个" + sex + "孩子，应该有" + age + "岁了";
            System.setOut(out);
            System.out.println("程序运行完毕，请查看日志");
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
    }
}