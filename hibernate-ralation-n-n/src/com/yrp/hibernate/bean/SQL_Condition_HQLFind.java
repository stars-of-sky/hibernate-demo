package com.yrp.hibernate.bean;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

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

    /*********************************************************
     * 查询
     * SQL、关系查询、HQL
     */

    /**
     * ！！打印对对象时，tostring（）方法不能(同时)有主外键
     * 重写的toString方法中不能有关联关系course属性，或者student
     * 不然栈溢出！！
     * java.lang.StackOverflowError
     */

    /************************
     * SQL查询：
     * ************
     */
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
        System.out.println(courses);
    }

    //模糊，传参数，查询
    @Test
    public void findLikeStudent() {
        String sql = "select *from student where name like ?";
        SQLQuery query = session.createSQLQuery(sql);
        List<Student> students = query.addEntity(Student.class).setString(0, "小%").list();
        System.out.println(students);
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
     * ************************
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
        System.out.println(students);

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
        System.out.println(s1);

        //大于25小于等于23
        Criteria criteria1 = session.createCriteria(Student.class);
        List<Student> s2 = criteria1.add(Restrictions.or(Restrictions.gt("age", 25), Restrictions.le("age", 23))).list();
        System.out.println(s2);
    }

    //按属性查询
    //可以封装方法传参使用
    @Test
    public void findStudentByProperty() {
        Criteria criteria = session.createCriteria(Student.class);
        String propertyName = "name";
        String propertyVlue = "安娜";
        List<Student> students = criteria.add(Restrictions.like(propertyName, propertyVlue)).list();
        System.out.println(students);
    }

    //按课程（对象）查询
    @Test
    public void findStudentByCourse() {
        Criteria criteria = session.createCriteria(Student.class);
        //courses为student课程的属性
        List<Student> students = criteria.createCriteria("courses").add(Restrictions.eq("name", "编程思想")).list();

            System.out.println(students);

    }

    //排序查询: asc , desc
    //追加形式
    @Test
    public void findStudentByCourseByasc() {
        Criteria criteria = session.createCriteria(Student.class);
        List<Student> students = criteria.addOrder(Order.asc("age")).createCriteria("courses").add(Restrictions.eq("name", "编程思想")).list();
            System.out.println(students);
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

            System.out.println(students);

        //按课程分页查询：
        Criteria criteria1 = session.createCriteria(Student.class);
        List<Student> students1 = criteria.addOrder(Order.asc("age")).createCriteria("courses")
                .add(Restrictions.eq("name", "编程思想"))
                .setFirstResult(0).setMaxResults(2).list();

            System.out.println(students1);
    }

    /**
     * 聚合函数
     */

    //男生人数：count
    @Test
    public void findCountStudent() {
        Criteria criteria = session.createCriteria(Student.class);
        Long count = (Long) criteria.setProjection(Projections.rowCount()).add(Restrictions.eq("sex", "男")).uniqueResult();
        System.out.println("男生总数：" + count);
    }

    @Test
    public void findStudentAvgAge() {
        Criteria criteria = session.createCriteria(Student.class);
//        Double age= (Double) criteria.setProjection(Projections.avg("age")).uniqueResult();

//        （都可以）或者这种写法：
        criteria.setProjection(Projections.avg("age"));
        Double age = (Double) criteria.uniqueResult();

        System.out.println("平均年龄：" + age);
    }

    /**************************************************************
     *
     * HQL查询：
     * ******************
     * ( select 别名 ) from 类名 where 属性 = 值
     * 括号里面的部分是可以省略的。
     */
    //获取所有学生
    @Test
    public void findAllStudent2() {
//        String hql="select s from Student s";
//        简化省略
        String hql = "from Student ";
        List<Student> students = session.createQuery(hql).list();

            System.out.println(students);

    }

    /**
     * 条件查询：
     */
    //id
    @Test
    public void findStudentById2() {
        String hql = "from Student where id=?";
        Student student = (Student) session.createQuery(hql).setInteger(0, 7).uniqueResult();
        System.out.println(student);
    }

    //通过对象类型的属性去查
//    @Test
//    public void findStudentByCourse2() {
//        String hql = "from Student where classInfo.name = ?";
//        List<Student> students = session.createQuery(hql).setString(0, "java进阶").list();
//        for (Student student : students) {
//            System.out.println(student);
//        }
//    }

    //通过集合类型的属性：
    @Test
    public void findStudentByCourse3() {         //HQL中查询参数的占位符":name"
        String hql = "from Student s inner join s.courses c where c.name = :name";
        List students = session.createQuery(hql).setString("name", "java进阶").list();
//        System.out.println(students.size());
        for (Object student : students) {
            System.out.println("OOOOOOOOOOOOO"+(Student)student);
        }

        Object o = session.createQuery(hql).setString("name", "java进阶").list();
        System.out.println(o);
    }

    @Test
    public void findCountStudentByCourse() {         //HQL中查询参数的占位符":name"
        String hql = "select avg(s.age) from Student s inner join s.courses c where c.name=:name";
        Double age = (Double) session.createQuery(hql).setString("name", "编程思想").uniqueResult();

        System.out.println(age);
    }

    //返回一部分字段的对象
    //!!!首先要有这些字段的构造函数
    @Test
    public void findStudent() {
        String hql = "select new Student(s.name,s.age) from Student s inner join s.courses c where c.name=:name order by s.age desc";
        List<Student> students = session.createQuery(hql).setString("name", "javaweb进阶").list();
        System.out.println("fucking!!!"+students);
//        for (Student student : students) {
//            System.out.println("HHHH"+student);
//        }

        String hql1 = "select new Student(s.name,s.age) from Student s order by s.age desc";
        List<Student> students1 = session.createQuery(hql1).list();

        System.out.println(students1);
//        for (Student student : students1) {
//            System.out.println(student);
//        }
    }

    //查询返回一个Map
    @Test


    public void findStudentMap(){
        String hql="select new map(s.id as id,s.name as name,s.sex as sex,s.age as age) from Student s order by s.age desc ";
        List<Map> list=session.createQuery(hql).list();
        for (Map map : list) {
            for (Object o : map.keySet()) {
                System.out.print(o+" : "+map.get(o)+"\t");
            }
            System.out.println();
        }
        System.out.println("size:"+list.size());
    }
}
