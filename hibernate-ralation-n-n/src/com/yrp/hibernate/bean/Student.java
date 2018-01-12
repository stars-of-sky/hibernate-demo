package com.yrp.hibernate.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * Create By Administrator.
 * Date:2018/1/11
 */

public class Student {
    private Integer id;
    private String name;
    private String sex;
    private Integer age;
    private Set<Course> courses = new HashSet<>();
    //用于一对一的学号
    private StudentNumber studentNumber;

    public StudentNumber getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(StudentNumber studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Student() {
    }

    public Student(String name, String sex, Integer age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Student{" +
//                "id=" + id +
                " name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", courses=" + courses +
                '}'+"\n";
    }
}
