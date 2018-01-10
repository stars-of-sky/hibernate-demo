package test.com.yrp.hibernate.bean;

import com.yrp.hibernate.bean.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


/**
 * User Tester.
 *
 * @author <Authors yrp>
 * @version 1.0
 * @since <pre>一月 10, 2018</pre>
 */
public class UserTest {

    @Before
    public void before() throws Exception {

    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getId()
     */
//    @Test
    public void testSaverUser() throws Exception {
        User user = new User();
        user.setName("夜星");
        user.setSex('男');
        user.setAge(28);
        user.setHeight(174.2);
        user.setWeight(66.6);
//        创建配置文件
        Configuration config = new Configuration();
//        加载配置文件（如果配置文件的名称不是默认的，需要调用有参的方法指定文件名 ）
        config.configure();
//        创建SessionFactory对象

        SessionFactory sessionFactory = config.buildSessionFactory(
                new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry());
//       获取session
        Session session = sessionFactory.openSession();
//       改动数据库， 开启事务
        Transaction transaction = session.beginTransaction();

        User user1 = new User("小强", '男', 28, 174.2, 66.6);

        session.save(user);
        session.save(user1);
        transaction.commit();
        session.close();
    }


    //    @Test
    public void testDeleteUser() throws Exception {
        Configuration config = new Configuration().configure();
        SessionFactory sessionFactory = config.buildSessionFactory(new ServiceRegistryBuilder()
                .applySettings(config.getProperties()).buildServiceRegistry());

        Session session = sessionFactory.openSession();
        session.beginTransaction();
//        先获取到ID=2的user对象
        User user2 = (User) session.get(User.class, 2);
//        再删除
        session.delete(user2);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }


//    @Test
    public void testUpdateUser() throws Exception {
        Configuration config = new Configuration().configure();
        SessionFactory sessionFactory = config.buildSessionFactory(new ServiceRegistryBuilder()
                .applySettings(config.getProperties()).buildServiceRegistry());
        Session session = sessionFactory.openSession();
//        更新数据，改变数据库，则开启事务
        session.beginTransaction();
//        获取对象，修改对象，upadte()
        User user = (User) session.get(User.class, 6);
        user.setAge(18);
        user.setName("熊大");

//        session.update(user);//可以不用！！！
        session.getTransaction().commit();//提交事务
        session.close();
        sessionFactory.close();
    }

    @Test
    public void testGetUser() throws Exception {
        System.out.println("fuck");
        Configuration config = new Configuration();
        config.configure();
        System.out.println("fuck!!!!");
        SessionFactory sessionFactory = config.buildSessionFactory(new ServiceRegistryBuilder()
                .applySettings(config.getProperties()).buildServiceRegistry());

        Session session = sessionFactory.openSession();

        User user = (User) session.get(User.class, 1);
        System.out.println(user);
        session.close();
    }

    @Test       //hibernate 的 HQL查询数据
    public void tesGetAllUser() throws Exception {
        Configuration config = new Configuration().configure();
        SessionFactory sessionFactory=config.buildSessionFactory(new ServiceRegistryBuilder()
                .applySettings(config.getProperties()).buildServiceRegistry());
        Session session=sessionFactory.openSession();

        //开启事务
        session.beginTransaction();
        String name="夜星";
        Query query= session.createQuery("from User where name like ?");

//        设置参数(和基1的PreparedStatement不一样，Query是基0的!!!!!
        query.setString(0,name);
        List<User> users=query.list();
        for (User user : users) {
            System.out.println(user);
        }
    }
}