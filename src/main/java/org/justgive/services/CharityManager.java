package org.justgive.services;

import org.justgive.filters.DatabaseFilter;
import org.justgive.models.Charity;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Jeffrey Risberg
 * @since April 2014
 */

public class CharityManager {
    static CharityManager instance = null;

    public static CharityManager getInstance() {
        if (instance == null) {
            instance = new CharityManager();
        }
        return instance;
    }

    /**
     * fetch a list
     */
    public List<Charity> findAll() {
        EntityManager em = DatabaseFilter.ENTITY_MANAGERS.get();

        List charities = em.createQuery("from Charity").getResultList();

        return charities;
    }

    /**
     * fetch one
     */
    public Charity findOne(Long id) {
        EntityManager em = DatabaseFilter.ENTITY_MANAGERS.get();

        Charity charity = em.find(Charity.class, id);
        return charity;
    }
}