package test.com.yrp.hibernate.bean;

import com.yrp.hibernate.bean.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



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
    @Test
    public void testSaverUser() throws Exception {
        User user=new User();
        user.setName("夜星");
        user.setSex('男');
        user.setAge(28);
        user.setHeight(174.2);
        user.setWeight(66.6);
        System.out.println(user);
//        创建配置文件
        Configuration config=new Configuration();
//        加载配置文件（如果配置文件的名称不是默认的，需要调用有参的方法指定文件名 ）
        config.configure();
//        创建SessionFactory对象
        SessionFactory sessionFactory=config.buildSessionFactory(
                new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry());
//       获取session
        Session session=sessionFactory.openSession();
//       改动数据库， 开启事务
        Transaction transaction=session.beginTransaction();

//        User user=new User("夜星",'男',28,174.2,66.6);

        session.save(user);
        transaction.commit();
        session.close();
    }


    @Test
    public void testGetUser() throws Exception {

        Configuration config=new Configuration();
        config.configure();
        SessionFactory sessionFactory=config.buildSessionFactory(new ServiceRegistryBuilder()
        .applySettings(config.getProperties()).buildServiceRegistry());
        Session session=sessionFactory.openSession();

    }
}