package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
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

        try (PreparedStatement statement = conn.prepareStatement(createTableSQL);){

            statement.executeUpdate();
            System.out.println("Table is created!");
        } catch (SQLException e) {
            System.out.println("Table is not created!");
        }
    }

    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS " + tableName;

        try (PreparedStatement statement = conn.prepareStatement(dropTableSQL);){

            statement.executeUpdate();
            System.out.println("Table is deleted!");
        } catch (SQLException e) {
            System.out.println("Table is not deleted!");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertTableSQL = "INSERT INTO " + tableName
                + "(name, lastName, age) " + "VALUES"
                + "('" + name + "','" + lastName + "'," + age +")";
        try (PreparedStatement statement = conn.prepareStatement(insertTableSQL);){

            statement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("User с именем – " + name + " не добавлен в базу данных");
        }

    }

    public void removeUserById(long id) {
        String removeUserByIdSQL = "DELETE FROM " + tableName + " where id = " + id;

        try (PreparedStatement statement = conn.prepareStatement(removeUserByIdSQL);){

            statement.executeUpdate();
            System.out.println("Record with id=" + id + " delete from table.");
        } catch (SQLException e) {
            System.out.println("Record with id=" + id + " delete from table.");
        }

}

    public List<User> getAllUsers() {
        List<User> userList = null;

        String getAllUsersSQL = "SELECT * from " + tableName;

        try (PreparedStatement statement = conn.prepareStatement(getAllUsersSQL);){
            try (ResultSet rs = statement.executeQuery()) {
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
            System.out.println("Error in receiving data.");
        }

        return userList;
    }

    public void cleanUsersTable() {
        String cleanUsersTableSQL = "TRUNCATE TABLE " + tableName;

        try (PreparedStatement statement = conn.prepareStatement(cleanUsersTableSQL);){

            statement.executeUpdate();
            System.out.println("Table is cleaned.");
        } catch (SQLException e) {
            System.out.println("Table is not cleaned.");
        }
    }
}
