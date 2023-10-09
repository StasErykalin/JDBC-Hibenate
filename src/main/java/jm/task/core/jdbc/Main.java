package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        jdbc();
    }
    public static void jdbc(){
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Аркадий", "Паровозов", (byte) 35);
        userService.saveUser("Петр", "Лавкин", (byte) 37);
        userService.saveUser("Анатолий", "Анатолий", (byte) 28);
        userService.saveUser("Владимир", "Ульянов", (byte) 9);
        List<User> userList = userService.getAllUsers();
        userList.forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
