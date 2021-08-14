package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {


    @Override
    public void createTable() {

        String query = "CREATE TABLE IF NOT EXISTS users_hibernate ("
                + "id BIGINT NOT NULL AUTO_INCREMENT,"
                + "firstName VARCHAR(45) NOT NULL,"
                + "lastName VARCHAR(45) NOT NULL,"
                + "age TINYINT(3) NOT NULL, PRIMARY KEY (id))";
        SessionFactory sessionFactory = Util.getSessionFactory();
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(query).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {

            try {
                transaction.rollback();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            e.printStackTrace();
        }
        transaction = null;
        sessionFactory.close();
    }

    @Override
    public void dropTable() {

        String query = "DROP TABLE IF EXISTS users_hibernate;";
        SessionFactory sessionFactory = Util.getSessionFactory();
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(query).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {

            try {
                transaction.rollback();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            e.printStackTrace();
        }
        transaction = null;
        sessionFactory.close();
    }

    @Override
    public void save(String name, String lastName, byte age) {

        SessionFactory sessionFactory = Util.getSessionFactory();
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception e) {

            try {
                transaction.rollback();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            e.printStackTrace();
        }
        transaction = null;
        sessionFactory.close();
    }

    @Override
    public void delete(long id) {

        String query = "delete User where id = :ID";
        SessionFactory sessionFactory = Util.getSessionFactory();
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery(query).setParameter("ID", id).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {

            try {
                transaction.rollback();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            e.printStackTrace();
        }
        transaction = null;
        sessionFactory.close();
    }

    @Override
    public List<User> getAll() {

        List<User> userList = new ArrayList<>();
        String query = "from User";
        SessionFactory sessionFactory = Util.getSessionFactory();
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            userList = session.createQuery(query).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {

            try {
                transaction.rollback();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            e.printStackTrace();
        }
        transaction = null;
        sessionFactory.close();
        return userList;
    }

    @Override
    public void cleanTable() {

        String query = "TRUNCATE TABLE users_hibernate";
        SessionFactory sessionFactory = Util.getSessionFactory();
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(query).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {

            try {
                transaction.rollback();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            e.printStackTrace();
        }
        transaction = null;
        sessionFactory.close();
    }

}
