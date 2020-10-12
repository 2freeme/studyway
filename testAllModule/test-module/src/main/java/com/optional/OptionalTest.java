package com.optional;

import com.stream.Student;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Author： Dingpengfei
 * @Description：测试 Optional 类
 * @Date： 2020-9-2 11:41
 */
public class OptionalTest {

    /***
     * 构造方法
     * 只有三种的构造方法
     */
    public void consMethod() {
        // 1、创建一个包装对象值为空的Optional对象
        Optional<Object> empty = Optional.empty();
        // 2、创建包装对象值 可以为空的对象 ofNullable null的话默认设置为empty
        Optional<String> a = Optional.ofNullable("a");
        Optional<Object> o = Optional.ofNullable(null);
        // 3、创建包装对象值允许为空的Optional对象
        //  如果为 null 的话则会抛出异常
        Optional<String> s = Optional.of(null);
        System.out.println("aaa");
    }

    /**
     * 测试 IfPresent  如果不为null的话就可以接着走下去，为空的话跳过
     */
    public void testIfPresent() {
        Student student = new Student();
        Student student1 = null;
        //为null的时候直接跳过。不为null的时候可以打印出来
        Optional.ofNullable(student1).ifPresent(
                u -> System.out.println("The student name is : " + u.getName()));

    }

    /**
     * 过滤
     */
    public void testFilter() {
        Student student = new Student();
        student.setAge(19);
        Optional.ofNullable(student).filter(
                u -> u.getAge() > 18).ifPresent(
                u -> System.out.println("The student age is more than 18."));
    }


    /**
     * 将其转化为map
     */
    public void testMap() {
        Student student = new Student();
        Optional<Integer> integer = Optional.ofNullable(student).map(u -> u.getAge());
        System.out.println(integer.toString()); //Optional.empty
    }

    /**
     * map()方法不同的是，入参Function函数的返回值类型为Optional<U>类型，而不是U类型，
     * 这样flatMap()能将一个二维的Optional对象映射成一个一维的对象
     */
    public void testFlatMap() {
        Student student = new Student();
        Optional<Integer> integer = Optional.ofNullable(student).flatMap(
                u -> Optional.ofNullable(u.getAge()));
        System.out.println(integer.toString());

    }

    /**
     * 默认返回里面的值，如果为空的话就返回默认的值
     */
    public void testOrElse() {
        Student student = new Student();
        //如果包装对象值非空，返回包装对象值，否则返回入参other的值（默认值）
        String unkown = Optional.ofNullable(student).map(
                u -> u.getName()).orElse("Unkown");
        System.out.println(unkown);
    }

    /**
     * orElseGet()方法与orElse()方法类似，
     * 区别在于orElseGet()方法的入参为一个Supplier对象，用Supplier对象的get()方法的返回值作为默认值。
     * 没啥用
     */
    public void testElseGet() {
        Student student = new Student();
        String s = Optional.ofNullable(student).map(
                u -> u.getName()).orElseGet(
                () -> "Unkown");
        System.out.println(s);
    }

    /**
     * 用于包装类的时候 判断为null的话 则抛出异常
     */
    public void testElseThrow() {
        Student student = new Student();
        Optional.ofNullable(student).map(
                u -> u.getName()).orElseThrow(
                () -> new RuntimeException("Unkown"));

    }

    public void test() {
        Student student = new Student();
        Optional.ofNullable(student).map(
                u -> u.getName()).orElseThrow(
                () -> new RuntimeException("Unkown"));

    }

    public static void main(String[] args) {
        OptionalTest optionalTest = new OptionalTest();
        //  optionalTest.testMap2();
        System.out.println(Optional.ofNullable(null));
        Map<String, Integer> map = new HashMap<>();
      //  int a = 1 + map.get("1");
      //  System.out.println(a);
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        System.out.println();

    }

    public void testMap2() {
        Student student = new Student();
        student.setName("a");
        Optional.of(student).filter(a -> !a.getName()
                .equals("a")).map(student1 -> student1.getName())
                .orElseThrow(() -> new RuntimeException("Unkown"));

        System.out.println(111);
    }
}
