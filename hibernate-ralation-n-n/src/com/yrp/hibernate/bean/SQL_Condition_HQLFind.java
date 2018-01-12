package com.yrp.hibernate.bean;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Id;
import java.util.List;
import java.util.Set;

/**
 * Create By Administrator.
 * Date:2018/1/12
 */

public class SQL_Condition_HQLFind {
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

    /**
     * 查询
     * SQL、关系查询、HQL
     */

    /**
     * ！！打印对对象时，tostring（）方法不能(同时)有主外键
     * 重写的toString方法中不能有关联关系course属性，或者student
     * 不然栈溢出！！
     * java.lang.StackOverflowError
     */
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

    @Test
    public void findAllStudent() {
        String sql = "select * from student";
        SQLQuery query = session.createSQLQuery(sql);
        List<Student> students = query.addEntity(Student.class).list();
        for (Student student : students) {
            System.out.println(student + "课程：" + student.getCourses());
        }
    }

    //查询课程
    @Test
    public void findAllCourse() {
        String sql = "select * from course";
        SQLQuery query = session.createSQLQuery(sql);
        //自动封装对象.addEntity(Course.class)
        List<Course> courses = query.addEntity(Course.class).list();
        for (Course cours : courses) {
            System.out.println(cours);
        }
    }

    //模糊，传参数，查询
    @Test
    public void findLikeStudent() {
        String sql = "select *from student where name like ?";
        SQLQuery query = session.createSQLQuery(sql);
        List<Student> students = query.addEntity(Student.class).setString(0, "小%").list();
        for (Student student : students) {
            System.out.println(student);
        }
    }

    //单个返回值(一条数据)，按id，name。。。。
    @Test
    public void findStudentById() {
        String sql = "select * from student where id= ?";
        SQLQuery query = session.createSQLQuery(sql);
        Student student = (Student) query.addEntity(Student.class).setParameter(0, 7).uniqueResult();
//        Student student= (Student) query.addEntity(Student.class).setInteger(0 ,7).uniqueResult();
        System.out.println(student);
    }

    //参数封装,不用指定类型，效果同上
    @Test
    public void findStudentByParameter() {
        String sql = "select * from student where sex= ? AND age=?";
        SQLQuery query = session.createSQLQuery(sql);
        Student student = (Student) query.addEntity(Student.class).setParameter(0, '男').setParameter(1, 27).uniqueResult();
        System.out.println(student);
    }

    /**
     * ********
     * 条件查询
     * ********
     */
    @Test
    public void findAllStudent1() {
        Criteria criteria = session.createCriteria(Student.class);
        List<Student> students = criteria.list();
        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println("课程：");
        Criteria criteria1 = session.createCriteria(Course.class);
        for (Object o : criteria1.list()) {
            System.out.println(o);
        }
    }

    @Test
    public void findStudentById1() {
        Criteria criteria = session.createCriteria(Student.class);
        //按参数查
        Student student = (Student) criteria.add(Restrictions.eq("id", 7)).uniqueResult();
        System.out.println(student);
        //按主键查  Restrictions.idEq()按照主键查（不一定是id）!!!!!
        Student s1 = (Student) criteria.add(Restrictions.idEq(12)).uniqueResult();
        System.out.println(s1);
    }

    //多条件拼接查询
    @Test
    public void finStudentByName() {
        Criteria criteria = session.createCriteria(Student.class);
        List<Student> students = criteria.add(
                //and 与 条件用法
                Restrictions.and(Restrictions.eq("sex", "男")
                        , Restrictions.like("name", "%星")))
                .list();
        for (Student student : students) {
            System.out.println(student);
        }

//        List<Student> s1=criteria.add(Restrictions.like("name","小%"))
//                .add(Restrictions.gt("age",20))
//                .add(Restrictions.le("age",27)).list();
//             for (Student student : s1) {
//            System.out.println(student);
//        }

    }

    /**
     * between  gt大于，le小于等于
     * 一个条件criteria对象只能对应一次查询
     */
    @Test
    public void find() {
        Criteria criteria = session.createCriteria(Student.class);
        //在20到25之间
        List<Student> s1 = criteria.add(Restrictions.like("name", "小%"))
                .add(Restrictions.between("age", 20, 25)).list();
        for (Student student : s1) {
            System.out.println(student);
        }
        //大于25小于等于23
        Criteria criteria1 = session.createCriteria(Student.class);
        List<Student> s2 = criteria1.add(Restrictions.or(Restrictions.gt("age", 25), Restrictions.le("age", 23))).list();
        for (Student student : s2) {
            System.out.println(student);
        }
    }

    //按属性查询
    //可以封装方法传参使用
    @Test
    public void findStudentByProperty() {
        Criteria criteria = session.createCriteria(Student.class);
        String propertyName = "name";
        String propertyVlue = "安娜";
        List<Student> students = criteria.add(Restrictions.like(propertyName, propertyVlue)).list();
        for (Student student : students) {
            System.out.println(student);
        }
    }

    //按课程（对象）查询
    @Test
    public void findStudentByCourse() {
        Criteria criteria = session.createCriteria(Student.class);
        //courses为student课程的属性
        List<Student> students = criteria.createCriteria("courses").add(Restrictions.eq("name", "编程思想")).list();
        for (Student student : students) {
            System.out.println(student);
        }
    }

    //排序查询: asc , desc
    //追加形式
    @Test
    public void findStudentByCourseByasc() {
        Criteria criteria = session.createCriteria(Student.class);
        List<Student> students = criteria.addOrder(Order.asc("age")).createCriteria("courses").add(Restrictions.eq("name", "编程思想")).list();
        for (Student student : students) {
            System.out.println(student);
        }
    }

    /**
     * 分页查询：
     * setFirstResult()当前页数据开始的地方
     * setMaxResult()每页最多显示多少条数据
     */
    @Test
    public void findStudentByPage() {
        Criteria criteria = session.createCriteria(Student.class);
        List<Student> students = criteria.setFirstResult(1).setMaxResults(3).list();
        for (Student student : students) {
            System.out.println(student);
        }

        //按课程分页查询：
        Criteria criteria1 = session.createCriteria(Student.class);
        List<Student> students1 = criteria.addOrder(Order.asc("age")).createCriteria("courses")
                .add(Restrictions.eq("name", "编程思想"))
                .setFirstResult(0).setMaxResults(2).list();
        for (Student student : students1) {
            System.out.println(student);
        }
    }

    /**
     * 聚合函数
     */

    //男生人数：
    @Test
    public void findCountStudent() {
        Criteria criteria = session.createCriteria(Student.class);
        Long count = (Long) criteria.setProjection(Projections.rowCount()).add(Restrictions.eq("sex", "男")).uniqueResult();
        System.out.println(count);
    }
}
