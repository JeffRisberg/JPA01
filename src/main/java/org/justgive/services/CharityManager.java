package org.justgive.services;

import org.justgive.database.DBException;
import org.justgive.database.DatabaseItemManager;
import org.justgive.filters.DatabaseFilter;
import org.justgive.model.Charity;

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
     * Get all charities in a range
     */
    public List<Charity> getAllCharities(int offset, int limit) throws CharityException {
        try {
            List<Charity> charities = DatabaseItemManager.getInstance().findAll(Charity.class, offset, limit);

            return charities;
        } catch (DBException e) {
            throw new CharityException(e);
        }
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