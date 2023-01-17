package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import java.sql.*;

public class Main {
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/Users";

    public static Connection getConnection () {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            System.out.println("Соединение с базой данных работает");
        } catch (SQLException e) {
            e.getStackTrace();
            System.err.println("Соединение с базой данных нет!!!");
        }
        return connection;
    }

    public static void main(String[] args) throws SQLException {
        UserDaoJDBCImpl myUser = new UserDaoJDBCImpl();
        myUser.createUsersTable();
        myUser.saveUser("Artem", "Karpov", (byte) 35);
        System.out.println("User с именем Artem добавлен в базу данных");
        myUser.saveUser("Ivanov", "Porfiriy", (byte) 85);
        System.out.println("User с именем Porfiriy добавлен в базу данных");
        myUser.saveUser("Zotov", "Kirill", (byte) 41);
        System.out.println("User с именем Kirill добавлен в базу данных");
        myUser.saveUser("Orlova", "Anna", (byte) 30);
        System.out.println("User с именем Anna добавлен в базу данных");
        System.out.println(myUser.getAllUsers());
        myUser.cleanUsersTable();
        myUser.dropUsersTable();
    }
}
