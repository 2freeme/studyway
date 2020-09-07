package com.stream;


import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author： Dingpengfei
 * @Description：在这里测试Stream 流
 * 这是lambda的一种语法糖，共有四种 1.类名::静态方法名，
 * 2.引用名(对象名) ::实例方法名，
 * 3.类名::实例方法名，
 * 4.类名::new（构造方法引用
 * @Date： 2020-8-20 14:30
 */
public class StreamForTest {


    private void streamCollection() {
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream(); //获取一个顺序流
        Stream<String> parallelStream = list.parallelStream(); //获取一个并行流
    }

    private void streamArray() {
        Integer[] nums = new Integer[10];
        Stream<Integer> stream = Arrays.stream(nums);
    }

    private void stream() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);

        Stream<Integer> stream2 = Stream.iterate(0, (x) -> x + 2).limit(6);
        stream2.forEach(System.out::println); // 0 2 4 6 8 10

        Stream<Double> stream3 = Stream.generate(Math::random).limit(2);
        stream3.forEach(System.out::println);
    }

    private void bufferReader() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("F:\\test_stream.txt"));
        Stream<String> lineStream = reader.lines();
        lineStream.forEach(System.out::println);
    }

    /**
     * 测试stream流的操作，先过滤、去重、跳过、限制
     * filter：过滤流中的某些元素
     * limit(n)：获取n个元素
     * skip(n)：跳过n元素，配合limit(n)可实现分页
     * distinct：通过流中元素的 hashCode() 和 equals() 去除重复元素
     */
    private void streamForFilter() {
        Stream<Integer> stream = Stream.of(6, 4, 6, 7, 3, 9, 8, 10, 12, 14, 14);

        Stream<Integer> newStream = stream.filter(s -> s > 5) //6 6 7 9 8 10 12 14 14
                .distinct() //6 7 9 8 10 12 14
                .skip(2) //9 8 10 12 14
                .limit(2); //9 8
        // newStream.forEach(System.out::println);

        //测试多个filter 前面的为空了的话 接下来是否会报错
        Stream<Integer> stream2 = Stream.of(6, 4, 6, 7, 3, 9, 8, 10, 12, 14, 14);
        stream2.filter(s -> s < 3).filter(a -> a < 3).forEach(System.out::println); //并不会报错

        //测试都过滤的话会怎么做，为空还是空集合，
        Stream<Integer> stream3 = Stream.of(6, 4, 6, 7, 3, 9, 8, 10, 12, 14, 14);
        List<Integer> collect = stream3.filter(s -> s < 3).collect(Collectors.toList());
        System.out.println(collect);   ///测试的数据为空集合  并不是null

        //anymatch
        Stream<Integer> stream4 = Stream.of(6, 4, 6, 7, 3, 9, 8, 10, 12, 14, 14);
        boolean b = stream4.anyMatch(u -> u == 12);
        System.out.println(b);

        //findany
        Stream<Integer> stream5 = Stream.of(6, 4, 6, 7, 3, 9, 8, 10, 12, 14, 14);
        // Optional<Integer> any = stream5.findAny(); //第一个
        // System.out.println(any.get());


    }


    /**
     * 转化map  映射
     * map：接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
     * flatMap：接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。
     */
    private void streamMap() {
        List<String> list = Arrays.asList("a,b,c", "1,2,3");

        //将每个元素转成一个新的且不带逗号的元素
        Stream<String> s1 = list.stream().map(s -> s.replaceAll(",", ""));
        s1.forEach(System.out::println); // abc  123

        Stream<String> s3 = list.stream().flatMap(s -> {
            //将每个元素转换成一个stream
            String[] split = s.split(",");
            Stream<String> s2 = Arrays.stream(split);
            return s2;
        });
        s3.forEach(System.out::println); // a b c 1 2 3
    }


    /**
     * sorted()：自然排序，流中元素需实现Comparable接口
     * sorted(Comparator com)：定制排序，自定义Comparator排序器
     */
    private void streamSort() {
        List<String> list = Arrays.asList("aa", "ff", "dd");
        //String 类自身已实现Compareable接口
        list.stream().sorted().forEach(System.out::println);// aa dd ff

        Student s1 = new Student("aa", 10);
        Student s2 = new Student("bb", 20);
        Student s3 = new Student("aa", 30);
        Student s4 = new Student("dd", 40);
        List<Student> studentList = Arrays.asList(s1, s2, s3, s4);

        //自定义排序：先按姓名升序，姓名相同则按年龄升序
        studentList.stream().sorted(
                (o1, o2) -> {
                    if (o1.getName().equals(o2.getName())) {
                        return o1.getAge() - o2.getAge();
                    } else {
                        return o1.getName().compareTo(o2.getName());
                    }
                }
        ).forEach(System.out::println);
    }


    /**
     * peek：如同于map，能得到流中的每一个元素。
     * 但map接收的是一个Function表达式，有返回值；而peek接收的是Consumer表达式，没有返回值。
     */
    private void streamPeek() {
        Student s1 = new Student("aa", 10);
        Student s2 = new Student("bb", 20);
        List<Student> studentList = Arrays.asList(s1, s2);

        studentList.stream()
                .peek(o -> o.setAge(100))
                .forEach(System.out::println);


        //Student{name='aa', age=100}
        //Student{name='bb', age=100}
    }

    /**
     * 终止操作
     * 匹配、聚合操作
     * allMatch：接收一个 Predicate 函数，当流中每个元素都符合该断言时才返回true，否则返回false
     * noneMatch：接收一个 Predicate 函数，当流中每个元素都不符合该断言时才返回true，否则返回false
     * anyMatch：接收一个 Predicate 函数，只要流中有一个元素满足该断言则返回true，否则返回false
     * findFirst：返回流中第一个元素
     * findAny：返回流中的任意元素
     * count：返回流中元素的总个数
     * max：返回流中元素最大值
     * min：返回流中元素最小值
     */
    public void streamStop() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        boolean allMatch = list.stream().allMatch(e -> e > 10); //false
        boolean noneMatch = list.stream().noneMatch(e -> e > 10); //true
        boolean anyMatch = list.stream().anyMatch(e -> e > 4);  //true

        Integer findFirst = list.stream().findFirst().get(); //1
        Integer findAny = list.stream().findAny().get(); //1

        long count = list.stream().count(); //5
        Integer max = list.stream().max(Integer::compareTo).get(); //5
        Integer min = list.stream().min(Integer::compareTo).get(); //1
    }


    /**
     * 测试的就是我们的集合中的问题
     * collect geng更像是一种方法的转换
     * 使用stream流效率稍低
     */
    public void testForList() {
        Student s1 = new Student("aa", 10);
        Student s2 = new Student("bb", 20);
        List<Student> studentList = Arrays.asList(s1, s2);

        /**
         *  .collect(Collectors.toMap(UserBo::getUserId, v -> v, (v1, v2) -> v1));
         第一个参数UserBo::getUserId 表示选择UserBo的getUserId作为map的key值；
         第二个参数v -> v表示选择将原来的对象作为map的value值；
         Function.identity 默认  等价于    v -> v
         第三个参数(v1, v2) -> v2中，如果v1与v2的key值相同，选择v2作为那个key所对应的value值 也就是新的代替旧的
         */
        long l = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            Map<String, Integer> collect = studentList.
                    stream().
                    collect(Collectors.toMap(Student::getName, Student::getAge,
                            (key1, key2) -> key2));
        }
        System.out.println("使用流 " + (System.currentTimeMillis() - l)); //使用流 382


        long l1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            Map<String, Integer> map = new HashMap<>();
            for (Student student : studentList) {
                map.put(student.getName(), student.getAge());
            }
        }
        System.out.println("使用循环 " + (System.currentTimeMillis() - l1)); //使用循环 159


        Map<String, Student> collect2;
        collect2 = studentList.
                stream().
                collect(Collectors.toMap(Student::getName, Function.identity(),
                        (oldValue, newValue) -> newValue));
        //System.out.println(JSON.toJSONString(collect));


        //针对于存在重复的值的时候
        //这个的话就是将相同的值放到一个map中
        Map<String, List<Student>> map =
                studentList.stream().collect(Collectors.toMap(Student::getName,
                        e -> {
                            ArrayList<Student> list = new ArrayList<>();
                            list.add(e);
                            return list;
                        },
                        (oldList, newList) -> {
                            oldList.addAll(newList);
                            return oldList;
                        }));


    }


    /**
     * 测试collect的方法
     * 转化
     * 在stream进行了一些列的操作了之后，可以用来还原的放法
     */
    public void testCollect() {
        Stream.of("1", "3", "3", "2").forEach(System.out::println);
        System.out.println(Stream.of("1", "3", "3", "2").collect(Collectors.joining(",")));
        Stream<Integer> stream = Stream.of(6, 4, 6, 7, 3, 9, 8, 10, 12, 14, 14);

        Stream<Integer> newStream = stream.filter(s -> s > 5) //6 6 7 9 8 10 12 14 14
                .distinct() //6 7 9 8 10 12 14
                .skip(2) //9 8 10 12 14
                .limit(2);
        List<Integer> collect = newStream.collect(Collectors.toList());
        System.out.println(JSON.toJSONString(collect));


    }


    /**
     * 测试最大值和最小值
     */
    public void testMinAndMax() {
        Stream<Integer> stream5 = Stream.of(6, 4, 6, 7, 3, 9, 8, 10, 12, 14, 14);
        int a = stream5.max(Integer::compare).get();
        System.out.println(a);  //14 直接取最大值

        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("1", 1));
        students.add(new Student("2", 2));
        students.add(new Student("3", 3));

        //Comparator 这个接口的话就是返回的是正负值的关系
        //和下面的是对等的关系
        Optional<Student> min = students.stream().min((b, c) -> {
            return b.getAge() - c.getAge();
        });
        System.out.println(min);

        //todo
        Optional<Student> min1 = students.stream().min(Comparator.comparing(Student::getAge));
        System.out.println(min1);
    }


    public static void main(String[] args) {
        StreamForTest streamForTest = new StreamForTest();
        streamForTest.testMinAndMax();
    }

}
