package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String table = "CREATE TABLE IF NOT EXISTS users ("
                + "id BIGINT NOT NULL AUTO_INCREMENT,"
                + "name VARCHAR(45) NOT NULL,"
                + "lastName VARCHAR(45) NOT NULL,"
                + "age TINYINT(3) NOT NULL, PRIMARY KEY (id))";
        Transaction transaction = null;
        SessionFactory sf = Util.getSessionFactory();
        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(table).executeUpdate();
            session.getTransaction().commit();
        } catch (Throwable e) {
            try {
                transaction.rollback();
            } catch (Exception exception) {
                System.out.println("При попытке роллбека произошла ошибка");
                exception.printStackTrace();
            }
            System.out.println("createUsersTable error");
        }
        sf.close();

    }

    @Override
    public void dropUsersTable() {
        String drop = "DROP TABLE IF EXISTS users;";
        Transaction transaction = null;
        SessionFactory sf = Util.getSessionFactory();
        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(drop).executeUpdate();
            session.getTransaction().commit();
        } catch (Throwable e) {
            try {
                transaction.rollback();
            } catch (Exception exception) {
                System.out.println("При попытке роллбека произошла ошибка");
                exception.printStackTrace();
            }
            System.out.println("dropUsersTable error");
        }
        sf.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        SessionFactory sf = Util.getSessionFactory();
        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Throwable e) {
            try {
                transaction.rollback();
            } catch (Exception exception) {
                System.out.println("При попытке роллбека произошла ошибка");
                exception.printStackTrace();
            }
            System.out.println("saveUser error");
        }
        sf.close();
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        SessionFactory sf = Util.getSessionFactory();
        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("delete User where id = :ID");
            query.setParameter("ID", id);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Throwable e) {
            try {
                transaction.rollback();
            } catch (Exception exception) {
                System.out.println("При попытке роллбека произошла ошибка");
                exception.printStackTrace();
            }
            System.out.println("removeUserById error");
        }
        sf.close();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        Transaction transaction = null;
        SessionFactory sf = Util.getSessionFactory();
        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            result = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
        } catch (Throwable e) {
            try {
                transaction.rollback();
            } catch (Exception exception) {
                System.out.println("При попытке роллбека произошла ошибка");
                exception.printStackTrace();
            }
            System.out.println("getAllUsers error");
        }
        sf.close();
        return result;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        String delete = "TRUNCATE TABLE users";
        SessionFactory sf = Util.getSessionFactory();
        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(delete).executeUpdate();
            session.getTransaction().commit();
        } catch (Throwable e) {
            try {
                transaction.rollback();
            } catch (Exception exception) {
                System.out.println("При попытке роллбека произошла ошибка");
                exception.printStackTrace();
            }
            System.out.println("cleanUsersTable error");
        }
        sf.close();
    }
}
