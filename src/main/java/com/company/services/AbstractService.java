package com.company.services;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Jeff Risberg
 * @since 11/11/17
 */
@Slf4j
public abstract class AbstractService<T> {

    static public EntityManagerFactory emf;

    public void doWork(Consumer<EntityManager> consumer) {
        EntityManager em = emf.createEntityManager();

        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            try {
                // call consumer
                consumer.accept(em);
                transaction.commit();
            } catch (Exception e) {
                log.error("Failed to execute transaction", e);
                transaction.rollback();
            }
        } finally {
            em.close();
        }
    }

    public void close() {
        emf.close();
    }

    public abstract T getById(Long id);

    public abstract List<T> getAll(int limit, int offset);

}
