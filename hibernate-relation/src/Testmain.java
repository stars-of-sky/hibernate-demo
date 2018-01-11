import com.yrp.hibernate.bean.ClassInfo;
import com.yrp.hibernate.bean.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class Testmain {
    Configuration config=null;
    Session session=null;
    @Before
    public void setUp() throws Exception {
        config = new Configuration().configure();
        SessionFactory sessionFactory=config.buildSessionFactory(new ServiceRegistryBuilder()
                .applySettings(config.getProperties()).buildServiceRegistry());
        session=sessionFactory.openSession();
        session.beginTransaction();
    }

    @After
    public void tearDown() throws Exception {
        session.close();
    }

    @Test
    public void AddStudent() throws Exception {
        Student student=new Student("小红","女",23);
//        session.beginTransaction();
        session.save(student);
        session.getTransaction().commit();

    }
    @Test
    public void createClassInfo() throws Exception{
        ClassInfo classInfo=new ClassInfo();
        classInfo=new ClassInfo();
        classInfo.setName("0703班");
//        session.beginTransaction();
        session.save(classInfo);
        session.getTransaction().commit();
    }

    @Test
    public void AddStudentToClass() throws Exception {
        ClassInfo classInfo= (ClassInfo) session.get(ClassInfo.class,2);
        Student student= (Student) session.get(Student.class,1);
        Student student1= (Student) session.get(Student.class,3);
//        开始class里面没有Set先new一个set
        Set<Student> students=new HashSet<>();

        classInfo.setStudentset(students);
        session.save(classInfo);

        classInfo.getStudentset().add(student);
        classInfo.getStudentset().add(student1);
        session.getTransaction().commit();
    }

//    移除某学生
    @Test
    public void deleteStudentFromClass(){
        Student student= (Student) session.get(Student.class,3);
        ClassInfo classInfo= (ClassInfo) session.get(ClassInfo.class,2);
        classInfo.getStudentset().remove(student);
        session.getTransaction().commit();
    }

//    清除班级内所学生
    @Test
    public void deleteAllStudentFromClass(){
        Student student= (Student) session.get(Student.class,3);
        ClassInfo classInfo= (ClassInfo) session.get(ClassInfo.class,2);
        classInfo.getStudentset().clear();
        session.getTransaction().commit();
    }

    @Test
    public void changeStudentFromClass(){
        Student student= (Student) session.get(Student.class,2);
        ClassInfo classInfo= (ClassInfo) session.get(ClassInfo.class,2);
        classInfo.getStudentset().add(student);
        session.getTransaction().commit();
    }

//    双向关联

//    学生这边操作
    @Test
    public void addStudentToClass() throws Exception{
        ClassInfo classInfo= (ClassInfo) session.get(ClassInfo.class,2);
        Student student= (Student) session.get(Student.class,5);
        student.setClassInfo(classInfo);
        session.getTransaction().commit();
    }
//    从班级中移除
    @Test
    public void removeStudentFromClass(){
        Student student= (Student) session.get(Student.class,1);
        student.setClassInfo(null);
        session.getTransaction().commit();
    }
//   同上 删除学生
    @Test
    public void deleteStudent(){
        Student student= (Student) session.get(Student.class,3);
        session.delete(student);
        session.getTransaction().commit();
    }

}