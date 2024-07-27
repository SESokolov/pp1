package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection conn = Util.getConnection();
    private static final String tableName = "users";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + "("
                + "id BIGSERIAL PRIMARY KEY NOT NULL, "
                + "name VARCHAR(20) NOT NULL, "
                + "lastName VARCHAR(20) NOT NULL, "
                + "age integer NOT NULL"
                + ")";

        try (Statement statement = conn.createStatement();){

            statement.execute(createTableSQL);
            System.out.println("Table \"" + tableName + "\" is created!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS " + tableName;

        try (Statement statement = conn.createStatement();){

            statement.execute(dropTableSQL);
            System.out.println("Table \"" + tableName + "\" is deleted!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertTableSQL = "INSERT INTO " + tableName
                + "(name, lastName, age) " + "VALUES"
                + "('" + name + "','" + lastName + "'," + age +")";
        try (Statement statement = conn.createStatement();){

            statement.execute(insertTableSQL);
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void removeUserById(long id) {
        String removeUserByIdSQL = "DELETE FROM " + tableName + " where id = " + id;

        try (Statement statement = conn.createStatement();){

            statement.execute(removeUserByIdSQL);
            System.out.println("Record with id=" + id + " delete from table \"" + tableName + "\".");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

}

    public List<User> getAllUsers() {
        List<User> userList = null;

        String getAllUsersSQL = "SELECT * from " + tableName;

        try (Statement statement = conn.createStatement();){
            try (ResultSet rs = statement.executeQuery(getAllUsersSQL)) {
                userList = new ArrayList<User>();

                while (rs.next()) {
                    Long userId = rs.getLong("id");
                    String userName = rs.getString("name");
                    String userLastName = rs.getString("lastName");
                    Byte userAge = rs.getByte("age");
                    User user = new User();
                    user.setId(userId);
                    user.setName(userName);
                    user.setLastName(userLastName);
                    user.setAge(userAge);
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return userList;
    }

    public void cleanUsersTable() {
        String cleanUsersTableSQL = "TRUNCATE TABLE " + tableName;

        try (Statement statement = conn.createStatement();){

            statement.execute(cleanUsersTableSQL);
            System.out.println("Table \"" + tableName + "\" is cleaned.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
