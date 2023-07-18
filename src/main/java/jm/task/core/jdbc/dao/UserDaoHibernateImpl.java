package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String query = """
                CREATE TABLE users (
                  id INT NOT NULL AUTO_INCREMENT,
                  name VARCHAR(45) NULL,
                  last_name VARCHAR(45) NULL,
                  age INT NULL,
                  PRIMARY KEY (id),
                  UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE);
                """;
            session.createSQLQuery(query).addEntity(User.class);
            transaction.commit();

            System.out.println("ez");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
