package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = getSessionFactory();
    private static final String tableName = "users";

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + "("
                + "id BIGSERIAL PRIMARY KEY NOT NULL, "
                + "name VARCHAR(20) NOT NULL, "
                + "lastName VARCHAR(20) NOT NULL, "
                + "age integer NOT NULL"
                + ")";
        try (Session session = sessionFactory.openSession();){
            session.beginTransaction();
            session.createSQLQuery(createTableSQL).executeUpdate();
            session.getTransaction().commit();
        } catch (org.hibernate.HibernateException e) {
            System.out.println("Table is not created.");
        }

    }

    @Override
    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS " + tableName;
        try (Session session = sessionFactory.openSession();){
            session.beginTransaction();
            session.createSQLQuery(dropTableSQL).executeUpdate();
            session.getTransaction().commit();
        } catch (org.hibernate.HibernateException e) {
            System.out.println("Table is not dropped");
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession();){
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (org.hibernate.HibernateException e) {
            System.out.println("User с именем – " + name + " не добавлен в базу данных");
        }

    }

    @Override
    public void removeUserById(long id) {
        String removeUserByIdSQL = "DELETE FROM " + tableName + " where id = " + id;

        try (Session session = sessionFactory.openSession();){
            session.beginTransaction();
            session.createQuery(removeUserByIdSQL).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Record with id=" + id + " delete from table.");
        } catch (org.hibernate.HibernateException e) {
            System.out.println("Record with id=" + id + " delete from table.");
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;

        String getAllUsersSQL = "from " + tableName;

        try (Session session = sessionFactory.openSession();){
            session.beginTransaction();
            userList = session.createQuery(getAllUsersSQL).getResultList();
            session.getTransaction().commit();
        } catch (org.hibernate.HibernateException e) {
            System.out.println("Error receiving records from table.");
        }

        return userList;

    }

    @Override
    public void cleanUsersTable() {
        String cleanUsersTableSQL = "DELETE FROM " + tableName;
        try (Session session = sessionFactory.openSession();) {
            session.beginTransaction();
            session.createQuery(cleanUsersTableSQL).executeUpdate();
            session.getTransaction().commit();
        } catch (org.hibernate.HibernateException e) {
            System.out.println("Error cleaning table.");
        }
    }
}
