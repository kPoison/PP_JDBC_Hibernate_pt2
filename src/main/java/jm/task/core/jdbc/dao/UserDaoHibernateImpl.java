package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    private void dataDefinition(String query) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery(query).addEntity(User.class).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void createUsersTable() {
        dataDefinition("""
                CREATE TABLE IF NOT EXISTS users (
                  id INT NOT NULL AUTO_INCREMENT,
                  name VARCHAR(45) NULL,
                  last_name VARCHAR(45) NULL,
                  age INT NULL,
                  PRIMARY KEY (id),
                  UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE);
                """);
    }

    @Override
    public void dropUsersTable() {
        dataDefinition("DROP TABLE IF EXISTS users");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(new User(name, lastName, age));

            transaction.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.delete(session.get(User.class, id));

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = new ArrayList<>();

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            users = session.createQuery("from User").getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        dataDefinition("TRUNCATE TABLE users");
    }
}
