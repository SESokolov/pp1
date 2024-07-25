package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static String dbUrl = "jdbc:postgresql://localhost:5432/pp1";
    private static String dbUsername = "postgres";
    private static String dbPassword = "Pgpass";

    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        }
        catch (SQLException e) {e.printStackTrace();}

        return conn;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration()
                        .setProperty("hibernate.hbm2ddl.auto", "update")
                        .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect")
//                        .setProperty("hibernate.show_sql", "false")
//                        .setProperty("hibernate.format_sql", "true")
//                        .setProperty("hibernate.order_updates", "true") //
                        .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                        .setProperty("hibernate.connection.url", dbUrl)
                        .setProperty("hibernate.connection.username",dbUsername)
                        .setProperty("hibernate.connection.password",dbPassword)
                        .setProperty("hibernate.connection.autocommit", "true")
//                        .configure()
                        ;

                configuration.addAnnotatedClass(User.class);
                sessionFactory = configuration.buildSessionFactory();

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }

}
