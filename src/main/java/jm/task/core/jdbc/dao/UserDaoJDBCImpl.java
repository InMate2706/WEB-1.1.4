package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    @Override
    public void createUsersTable() {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users(" +
                "id BIGINT primary key auto_increment, name VARCHAR(30), lastName VARCHAR(30), age TINYINT);";

        try (Statement statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String DROP_TABLE = "DROP TABLE IF EXISTS users";

        try (Statement statement = connection.createStatement()) {
            statement.execute(DROP_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String INSERT_NEW = "INSERT INTO users(name, lastname, age) " +
                "VALUES(?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(INSERT_NEW)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        String DELETE = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        String GET_ALL = "SELECT* FROM users";

        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                byte age = resultSet.getByte("age");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastname");

                User user = new User(name, lastName, age);
                user.setId(id);
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String CLEAN_TABLE = "TRUNCATE TABLE users";

        try (Statement statement = connection.createStatement()) {
            statement.execute(CLEAN_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
