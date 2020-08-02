package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users(" +
                "id BIGINT primary key auto_increment, name VARCHAR(30)," +
                " lastName VARCHAR(30), age TINYINT);";
        Session session = Util.createSessionFactory().openSession();
        session.createSQLQuery(CREATE_TABLE).executeUpdate();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        String DROP_TABLE = "DROP TABLE IF EXISTS users";
        Session session = Util.createSessionFactory().openSession();
        session.createSQLQuery(DROP_TABLE).executeUpdate();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.createSessionFactory().openSession();
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.createSessionFactory().openSession();
        session.beginTransaction();
        User user = (User) session.get(User.class, id);
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.createSessionFactory().openSession();
        List<User> users = session.createSQLQuery("SELECT* FROM users")
                .addEntity(User.class).list();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.createSessionFactory().openSession();
        session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
        session.close();
    }
}
