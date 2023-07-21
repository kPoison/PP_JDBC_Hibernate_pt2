package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService service = new UserServiceImpl();
        service.createUsersTable();

        for (int i = 0; i < 4; i++) {
            service.saveUser("name" + i, "lastName" + i, (byte) i);
        }

        for(User user : service.getAllUsers()) {
            System.out.println(user);
        }

        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
