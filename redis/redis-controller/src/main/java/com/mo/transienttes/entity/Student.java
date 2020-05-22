package com.more.transienttes.entity;

import java.io.Serializable;

/**
 * @Author： Dingpengfei
 * @Description：  测试序列的使用
 * @Date： 2020-5-12 9:47
 */
public class Student implements Serializable {
    private transient String name;
    private String grade;
    private String age;

    public String getAge() {
        return age;
    }

    public Student(String name, String grade, String age) {
        this.name = name;
        this.grade = grade;
        this.age = age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Student(String name, String grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
