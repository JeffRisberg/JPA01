package org.justgive.services;

import org.justgive.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jeffrey Risberg
 * @since April 2014
 */

public class UserManager {
    static UserManager instance = null;

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    // fetch a list
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        User user;

        user = new User("John", "Smith", "");
        user.setId(1L);
        users.add(user);

        user = new User("Bob", "Jones", "");
        user.setId(2L);
        users.add(user);
        return users;
    }

    // fetch one
    public User findOne(Long id) {
        if (id == 0) {
            return new User("John", "Smith", "");
        } else {
            return new User("Bob", "Jones", "");
        }
    }
}