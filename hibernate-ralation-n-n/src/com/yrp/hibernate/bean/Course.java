package com.yrp.hibernate.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * Create By Administrator.
 * Date:2018/1/11
 */

public class Course {
    private Integer id;
    private String name;
    private Set<Student> students = new HashSet<>();

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

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
