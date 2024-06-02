package org.megaline.tui;

import org.megaline.core.dao.UserDao;
import org.megaline.core.models.User;

public class Test {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        User user = new User("amir", "some streeet", "id2929696");
    }
}
