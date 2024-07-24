package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static String dbUrl = "jdbc:postgresql://localhost:5432/pp1";
    private static String dbUsername = "postgres";
    private static String dbPassword = "Pgpass";

    public static Connection getConnection() {
//        Class.forName("org.postgresql.Driver");
//        System.out.println("Драйвер подключен");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        }
        catch (SQLException e) {e.printStackTrace();}

        return conn;
    }

}
