package org.justgive.services;

import org.justgive.filters.DatabaseFilter;
import org.justgive.models.JGSession;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Jeffrey Risberg
 * @since September 2014
 */

public class JGSessionManager {
    static JGSessionManager instance = null;

    public static JGSessionManager getInstance() {
        if (instance == null) {
            instance = new JGSessionManager();
        }
        return instance;
    }

    /**
     * fetch a list
     */
    public List<JGSession> findAll() {
        EntityManager em = DatabaseFilter.ENTITY_MANAGERS.get();

        List jgSessions = em.createQuery("select u from JGSession u").getResultList();

        return jgSessions;
    }

    /**
     * fetch one
     */
    public JGSession findOne(Long id) {
        EntityManager em = DatabaseFilter.ENTITY_MANAGERS.get();

        JGSession jgSession = em.find(JGSession.class, id);
        return jgSession;
    }

    /**
     * fetch one
     */
    public JGSession findOne(String jSessionId) {
        EntityManager em = DatabaseFilter.ENTITY_MANAGERS.get();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<JGSession> criteria = builder.createQuery(JGSession.class);
        Root<JGSession> root = criteria.from(JGSession.class);

        Path<String> rootjSessionId = root.get("jSessionId");
        criteria.where(builder.equal(rootjSessionId, jSessionId));

        try {
            return em.createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * persist one
     */
    public void save(JGSession jgSession) {
        EntityManager em = DatabaseFilter.ENTITY_MANAGERS.get();

        em.persist(jgSession);
    }

    /**
     * merge one
     */
    public void update(JGSession jgSession) {
        EntityManager em = DatabaseFilter.ENTITY_MANAGERS.get();

        em.merge(jgSession);
    }

    /**
     * delete one
     */
    public void delete(JGSession jgSession) {
        EntityManager em = DatabaseFilter.ENTITY_MANAGERS.get();

        em.remove(jgSession);
    }
}