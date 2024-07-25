package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();

        userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivonov", (byte) 23);
        userService.saveUser("Semen", "Petrov", (byte) 21);
        userService.saveUser("Anna", "Kurova", (byte) 24);
        userService.saveUser("Sonya", "Dashi", (byte) 22);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
