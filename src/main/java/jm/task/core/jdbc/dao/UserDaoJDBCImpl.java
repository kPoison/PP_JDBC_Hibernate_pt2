package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    private void dataDefinition(String query) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException ignored) {

        }
    }

    public void createUsersTable() {
        dataDefinition("""
                CREATE TABLE users (
                  id INT NOT NULL AUTO_INCREMENT,
                  name VARCHAR(45) NULL,
                  last_name VARCHAR(45) NULL,
                  age INT NULL,
                  PRIMARY KEY (id),
                  UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE);
                """
        );
    }

    public void dropUsersTable() {
        dataDefinition("DROP TABLE users");
    }

    public void saveUser(String name, String lastName, byte age) {
        final String query = "INSERT INTO users(name, last_name, age) VALUES('"
                + name + "', '"
                + lastName + "', '"
                + age + "')";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();

            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException ignored) {

        }
    }

    public void removeUserById(long id) {
        dataDefinition("DELETE FROM users WHERE id = " + id);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        final String query = "SELECT name, last_name, age FROM users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age")
                ));
            }

        } catch (SQLException ignored) {

        }

        return users;
    }

    public void cleanUsersTable() {
        dataDefinition("TRUNCATE TABLE users");
    }
}
