package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.Main;
import jm.task.core.jdbc.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Main.getConnection(); Statement st = connection.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS Users (id TINYINT NOT NULL AUTO_INCREMENT," + "name VARCHAR(64) NOT NULL, lastname VARCHAR(64) NOT NULL, age TINYINT NOT NULL, PRIMARY KEY (id))");
            connection.commit();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Main.getConnection(); Statement st = connection.createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS users");
            connection.commit();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Main.getConnection(); PreparedStatement ps = connection.prepareStatement("INSERT INTO Users (name, lastname, age) VALUES (?,?,?)")) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Main.getConnection(); PreparedStatement ps = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public List<User> getAllUsers() {
        try (Connection connection = Main.getConnection(); Statement st = connection.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM users")) {
            List<User> result = new ArrayList<>();
            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String lastname = rs.getString("lastname");
                byte age = rs.getByte("age");
                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setLastName(lastname);
                user.setAge(age);
                result.add(user);
            }
            connection.commit();
            return result;
        } catch (SQLException e) {
            e.getStackTrace();
            return null;
        }
    }


    public void cleanUsersTable() {
        try (Connection connection = Main.getConnection(); Statement st = connection.createStatement()) {
            st.executeUpdate("DELETE FROM users");
            connection.commit();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }
}
