package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String createUsersTable = "CREATE TABLE IF NOT EXISTS person " +
            "(ID SERIAL PRIMARY KEY," +
            " NAME TEXT, " +
            "LASTNAME TEXT, " +
            "AGE INT)";
    private static final String dropUsersTable = "DROP TABLE IF EXISTS person";
    private static final String getAllUsers = "SELECT * FROM person";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate(createUsersTable);
            connection.commit();
        } catch (SQLException e) {
            Util.rollback(connection);
            e.printStackTrace();
        } finally {
            Util.close(connection);
        }
    }

    public void dropUsersTable() {
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate(dropUsersTable);
            connection.commit();
        } catch (SQLException e) {
            Util.rollback(connection);
            e.printStackTrace();
        } finally {
            Util.close(connection);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getConnection();
        try ( PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO person (name, lastname, age) VALUES (?, ?, ?)")){
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            Util.rollback(connection);
            e.printStackTrace();
        } finally {
            Util.close(connection);
        }
    }

    public void removeUserById(long id) {
        Connection connection = Util.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM person WHERE id = ?")){
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            Util.rollback(connection);
            e.printStackTrace();
        } finally {
            Util.close(connection);
        }
    }

    public List<User> getAllUsers() {
        List<User> persons = new ArrayList<>();
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(getAllUsers);
            while (resultSet.next()) {
                persons.add(
                        new User(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getString("lastname"),
                                resultSet.getByte("age")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Util.close(connection);
        }
        return persons;
    }

    public void cleanUsersTable() {
        Connection connection = Util.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("TRUNCATE TABLE person")){
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            Util.rollback(connection);
            e.printStackTrace();
        }finally {
            Util.close(connection);
        }
    }
}
