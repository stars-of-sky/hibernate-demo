package com.yrp.hibernate.bean;

import java.util.Set;

/**
 * Create By Administrator.
 * Date:2018/1/11
 */

public class ClassInfo {
    private Integer id;
    private String name;
    private Set<Student> studentset;

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

    public Set<Student> getStudentset() {
        return studentset;
    }

    public void setStudentset(Set<Student> studentset) {
        this.studentset = studentset;
    }
}
