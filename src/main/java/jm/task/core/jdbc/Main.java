package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Andrey", "Tihonov", (byte) 29);
        userService.saveUser("Roman", "Alekseev", (byte) 23);
        userService.saveUser("Egor", "Olenikov", (byte) 25);
        userService.saveUser("Dmitriy", "Makarov", (byte) 57);

        List<User> users = userService.getAllUsers();
        users.stream().forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();

        Util.closeSessionFactory();
    }
}
