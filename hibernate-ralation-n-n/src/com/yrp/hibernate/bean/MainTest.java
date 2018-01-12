package com.yrp.hibernate.bean;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;


public class MainTest {
    Configuration config;
    Session session;

    @Before
    public void setUp() throws Exception {
        config = new Configuration().configure();
        SessionFactory sessionFactory = config.buildSessionFactory(new ServiceRegistryBuilder()
                .applySettings(config.getProperties()).buildServiceRegistry());
        session = sessionFactory.openSession();
        session.beginTransaction();

    }

    @After
    public void tearDown() throws Exception {
        session.getTransaction().commit();
        session.close();
    }

    //    注意course不能重复，会报错Duplicate entry 'java进阶' for key 'UK_topojlrn80k8ohbvly4jj7t6f'
    @Test
    public void addCourse() {
        Course course=new Course();
        course.setName("javaweb进阶");
        session.save(course);

//        Course course1 = new Course();
//        course1.setName("编程思想");
//        session.save(course1);

    }

    @Test
    public void addStudent() {
        Student student = new Student("安娜", "女", 23);
        session.save(student);

    }

    @Test
    public void choiceCourse() {
        Set<Course> courses = new HashSet<>();

        Student student = (Student) session.get(Student.class, 14);
        student.setCourses(courses);

        Course course = (Course) session.get(Course.class, 1);
        courses.add(course);
//        courses.add((Course) session.get(Course.class,2));
//        session.save(courses);
//        学生选课
        student.getCourses().add(course);
        student.getCourses().add((Course) session.get(Course.class, 7));

//        课程加入学生
        course.setStudents(new HashSet<Student>());
//        course.getStudents().add((Student) session.get(Student.class,2));
        Student student1 = new Student("熊大", "男", 20);
        session.save(student1);
        course.getStudents().add(student1);
        //        course.getStudents().add((Student) session.get(Student.class,3));


//        Student s1=new Student();
//        s1.setName("lisi");
//        Course c1=new Course();
//        c1.setName("English");
//        Course c2=new Course();
//        c2.setName("science");
//        s1.setCourses(new HashSet<Course>());
//        c1.setStudents(new HashSet<Student>());
//        c2.setStudents(new HashSet<Student>());
//        s1.getCourses().add(c1);
//        s1.getCourses().add(c2);
//        c1.getStudents().add(s1);
//        c2.getStudents().add(s1);
//
//        session.save(c1);
//        session.save(s1);
//        session.getTransaction().commit();
    }

    //        无效啊？？？？？？？？？？？？？？？？？？
    @Test
    public void deleteStudent() {
        Student student = (Student) session.get(Student.class, 1);
        System.out.println(student.getName());
//        这会删除学生1，并且会删除关联关系
        session.delete(student);
    }

    @Test
    public void deleteCourse() {
        Course course = (Course) session.get(Course.class, 3);
        System.out.println(course.getName());
        Student student = (Student) session.get(Student.class, 3);
//        course.getStudents().remove(course);
        System.out.println("KKKKKKK");
        student.getCourses().remove(course);
//       inverse="true"时！！ 不能像以下方式删除自己，因为有外键关联，报错
        session.delete(course);
    }


    @Test
    public void getAllCourse() {
        Student student = (Student) session.get(Student.class, 1);
        Set<Course> courses = student.getCourses();
        for (Course cours : courses) {
            System.out.println(cours.getName());
        }

        Course course = (Course) session.get(Course.class, 3);
        Set<Student> students = course.getStudents();
        for (Student student1 : students) {
            System.out.println(student1.getName());
        }
    }

    /**
     * 查询
     * SQL、关系查询、HQL
     * 见SQL Find
     */

    /**
     * ！！打印对对象时，tostring（）方法不能(同时)有主外键
     * 重写的toString方法中不能有关联关系course属性，或者student
     * 不然栈溢出！！
     * java.lang.StackOverflowError
     */

}
