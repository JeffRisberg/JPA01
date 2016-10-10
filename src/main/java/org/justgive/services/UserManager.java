package org.justgive.services;

import org.justgive.filters.DatabaseFilter;
import org.justgive.model.Donor;

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
    public List<Donor> findAll() {
        EntityManager em = DatabaseFilter.ENTITY_MANAGERS.get();

        List users = em.createQuery("select u from Donor u").getResultList();

        return users;
    }

    /**
     * fetch one
     */
    public Donor findOne(Long id) {
        EntityManager em = DatabaseFilter.ENTITY_MANAGERS.get();

        Donor donor = em.find(Donor.class, id);
        return donor;
    }

    /**
     * persist one
     */
    public void save(Donor donor) {
        EntityManager em = DatabaseFilter.ENTITY_MANAGERS.get();

        em.persist(donor);
    }

    /**
     * merge one
     */
    public void update(Donor donor) {
        EntityManager em = DatabaseFilter.ENTITY_MANAGERS.get();

        em.merge(donor);
    }

    /**
     * delete one
     */
    public void delete(Donor donor) {
        EntityManager em = DatabaseFilter.ENTITY_MANAGERS.get();

        em.remove(donor);
    }
}