package org.justgive.services;

import org.justgive.filters.DatabaseFilter;
import org.justgive.models.User;

import javax.persistence.EntityManager;
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

    /**
     * fetch a list
     */
    public List<User> findAll() {
        EntityManager em = DatabaseFilter.ENTITY_MANAGERS.get();

        List users = em.createQuery("select u from User u").getResultList();

        return users;
    }

    /**
     * fetch one
     */
    public User findOne(Long id) {
        EntityManager em = DatabaseFilter.ENTITY_MANAGERS.get();

        User user = em.find(User.class, id);
        return user;
    }
}