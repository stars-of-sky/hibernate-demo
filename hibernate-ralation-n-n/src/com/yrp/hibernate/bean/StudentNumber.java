package com.yrp.hibernate.bean;

/**
 * Create By yrp.
 * Date:2018/1/13
 */

public class StudentNumber {
    private Integer id;
    private Integer Number;
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return Number;
    }

    public void setNumber(Integer number) {
        Number = number;
    }
}
