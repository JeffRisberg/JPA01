package org.justgive.database;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;

/**
 * A DBTransaction is a set of access methods around an entity manager and the current transaction.
 *
 * @author Jeff
 * @since late 2014
 */
public class DBTransaction {
    private static Logger jgLog = LoggerFactory.getLogger(DBTransaction.class);

    private EntityManager em;
    private EntityTransaction transaction;

    protected DBTransaction() {
    }

    public DBTransaction(EntityManager em) {
        this.em = em;
        this.transaction = em.getTransaction();
    }

    public void begin() throws DBException {
        checkDatabase();
    }

    public void commit() throws DBException {
        checkDatabase();

        try {
            jgLog.trace("Committing Transaction");
            transaction.commit();
            jgLog.trace("Transaction Committed");

            // Expire the cache after a transaction commit
            // CacheManager cm = database.getCacheManager();
            // cm.expireCache();
        } catch (Exception e) {
            logBatchUpdateException(e);
            throw new DBException(e);
        }
    }

    public void rollback() throws DBException {
        checkDatabase();

        try {
            transaction.rollback();
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    /**
     * @return true if the database is not null and not closed/committed/rolled back
     */
    public boolean isActive() {
        return (transaction != null && transaction.isActive());
    }

    public EntityManager getEntityManager() {
        return em;
    }

    /**
     * @return true if the database is null or closed
     */
    public boolean isClosed() {
        return (em == null || !em.isOpen());
    }

    /**
     * The JPA implementation of the clear method obtains the
     * EntityManager and clears the cache.
     *
     * @throws org.justgive.database.DBException
     */
    public void clear() throws DBException {
        checkDatabase();

        try {
            em.clear();
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    /**
     * Creates a database record for the given object.
     *
     * @param object The object record to store in the database
     * @throws org.justgive.database.DBException
     */
    public void create(Object object) throws DBException {
        checkDatabase();

        try {
            em.persist(object);
        } catch (Exception e) {
            jgLog.error("DBTransaction.create()");
            jgLog.error(e);
            throw new DBException(e);
        }
    }

    /**
     * Reintroduces an object from memory into the current transaction.  Should never be
     * necessary under our current model of using only a single entity manager.
     *
     * @param object The object record to update
     * @throws org.justgive.database.DBException
     */
    public void update(Object object) throws DBException {
        checkDatabase();

        try {
            //if object already exists in current persistence context
            //ignore update, changes are automatically committed.
            if (!em.contains(object)) em.refresh(object);
        } catch (Exception e) {
            jgLog.error("DBTransaction.update()");
            jgLog.error(e);
            throw new DBException(e);
        }
    }

    /**
     * Deletes an object's database record.
     *
     * @param object The object record to delete
     * @throws org.justgive.database.DBException
     */
    public void delete(Object object) throws DBException {
        checkDatabase();

        try {
            em.remove(object);
        } catch (Exception e) {
            jgLog.error("DBTransaction.delete()");
            jgLog.error(e);
            throw new DBException(e);
        }
    }

    /**
     * Refreshes an object with data from the database
     *
     * @param object to refresh
     * @throws DBException
     */
    public <T> T refresh(T object) throws DBException {
        try {
            em.refresh(object);
        } catch (Exception e) {
            jgLog.error("DBTransaction.refresh()");
            jgLog.error(e);
            throw new DBException(e.getMessage());
        }
        return object;
    }

    /**
     * Returns an object of the class type given for a given primary key value.
     *
     * @param classObject The Class type to return.
     * @param id          The primary key value of the object.
     * @return Object
     * @throws org.justgive.database.DBException
     */
    public <T> T getObjectById(Class<T> classObject, int id) throws DBException {
        checkDatabase();

        try {
            return em.find(classObject, id);
        } catch (Exception e) {
            jgLog.error("DBTransaction.getObjectById()");
            jgLog.error(e);
            throw new DBException(e);
        }
    }

    public <T> T load(Class<T> classObject, int id) throws DBException {
        return getObjectById(classObject, id);
    }

    /**
     * Returns an object for a query string.
     *
     * @param classObject type of Object to return
     * @param queryString The SQL query string to execute.
     * @return The first object in the query's result set
     * @throws org.justgive.database.DBException on any exception.
     */
    public <T> List<T> getObjects(Class<T> classObject, String queryString) throws DBException {
        return getObjects(classObject, queryString, null);
    }

    /**
     * Returns an object for a query string.
     *
     * @param classObject type of Object to return
     * @param queryString The SQL query string to execute.
     * @return The first object in the query's result set
     * @throws org.justgive.database.DBException on any exception.
     */
    public <T> T getObject(Class<T> classObject, String queryString) throws DBException {
        List<T> returnObjects = getObjects(classObject, queryString);

        T returnObject = null;
        if (returnObjects.size() > 0) {
            returnObject = returnObjects.get(0);
        }
        return returnObject;
    }

    /**
     * Returns a result set of objects for the given SQL query string.
     *
     * @param queryString   The SQL query string to execute.
     * @param bindVariables A List of bound variables associated with
     *                      the query string.
     * @return The result set List.
     * @throws org.justgive.database.DBException on any exception
     */
    public <T> List<T> getObjects(Class<T> classObject, String queryString, List bindVariables) throws DBException {
        if (queryString == null) {
            throw new DBException("Null query");
        }

        checkDatabase();

        int offset = 0;
        int limit = 0;

        return getQueryResults(getJPAQuery(em, queryString, bindVariables), offset, limit);
    }

    /**
     * Returns a result set of objects for the given SQL query string.
     *
     * @param queryString   The SQL query string to execute.
     * @param bindVariables A List of bound variables associated with the query string.
     * @param offset
     * @param limit
     * @return The result set List.
     * @throws org.justgive.database.DBException on any exception
     */
    public <T> List<T> getObjects(Class<T> classObject, String queryString, List bindVariables, int offset, int limit) throws DBException {
        if (queryString == null) {
            throw new DBException("Null query");
        }

        checkDatabase();

        return getQueryResults(getJPAQuery(em, queryString, bindVariables), offset, limit);
    }

    private <T> List<T> getQueryResults(Query query, int offset, int limit)
            throws DBException {

        List<T> returnObjects = new ArrayList<>();
        try {
            if (offset > 0) query.setFirstResult(offset);
            if (limit > 0) query.setMaxResults(limit);
            @SuppressWarnings({"unchecked"})
            List<T> results = (List<T>) query.getResultList();

            for (T object : results) {
                returnObjects.add(object);
            }
        } catch (Exception e) {
            jgLog.error("DBTransaction.getQueryResults()");
            logBatchUpdateException(e);
            throw new DBException(e);
        }

        return returnObjects;
    }

    private void logBatchUpdateException(Exception e) {
        jgLog.error(e);
        if (e instanceof BatchUpdateException) {
            jgLog.error("BatchUpdateException.getNextException()");
            jgLog.error(((BatchUpdateException) e).getNextException());
        }
    }

    private Query getJPAQuery(EntityManager database, String queryString, List<Object> bindVariables)
            throws DBException {
        Query query;
        try {
            query = database.createQuery(queryString);
        } catch (Exception e) {
            jgLog.error("DBTransaction.getJPAQuery()");
            jgLog.error(e);
            throw new DBException(e);
        }

        if (bindVariables != null) {
            String debugOutput = new String(queryString);
            int position = 1;
            for (Object bindVariable : bindVariables) {
                query.setParameter(position, bindVariable);
                if (jgLog.isTraceEnabled()) {
                    debugOutput = debugOutput.replace("?" + position, "[" + bindVariable.getClass().getSimpleName() + "]" + bindVariable.toString());
                }
                position++;
            }
            if (jgLog.isTraceEnabled()) jgLog.trace(debugOutput);
        }

        return query;
    }

    /**
     * Returns an object for a query string.
     *
     * @param classObject type of Object to return
     * @param queryString The SQL query string to execute.
     * @return The first object in the query's result set
     * @throws org.justgive.database.DBException on any exception.
     */
    public <T> T getObject(Class<T> classObject, String queryString, List bindVariables) throws DBException {
        List<T> returnObjects = getObjects(classObject, queryString, bindVariables);

        T returnObject = null;
        if (returnObjects.size() > 0) {
            returnObject = returnObjects.get(0);
        }
        return returnObject;
    }

    /**
     * Returns an object for a query string.
     *
     * @param classObject type of Object to return
     * @param queryString The SQL query string to execute.
     * @return The first object in the query's result set
     * @throws org.justgive.database.DBException on any exception.
     */
    public <T> List<T> getObjects(Class<T> classObject, String queryString, Object bindVariable) throws DBException {
        List<Object> bindVariables = new ArrayList<>();
        bindVariables.add(bindVariable);

        return getObjects(classObject, queryString, bindVariables);
    }

    /**
     * Returns an object for a query string.
     *
     * @param classObject type of Object to return
     * @param queryString The SQL query string to execute.
     * @return The first object in the query's result set
     * @throws org.justgive.database.DBException on any exception.
     */
    public <T> T getObject(Class<T> classObject, String queryString, Object bindVariable) throws DBException {
        List<T> returnObjects = getObjects(classObject, queryString, bindVariable);

        T returnObject = null;
        if (returnObjects.size() > 0) {
            returnObject = returnObjects.get(0);
        }
        return returnObject;
    }

    /**
     * Returns a single Object of Class T from persistence using a raw sql statement.
     *
     * @param classType Class type to find
     * @param sqlQuery  raw sql statement
     */
    public <T> T queryObject(Class<T> classType, String sqlQuery) throws DBException {
        try {
            Query query = em.createNativeQuery(sqlQuery, classType);

            //noinspection unchecked
            return (T) query.getSingleResult();
        } catch (Exception e) {
            throw new DBException(e.getMessage());
        }
    }

    /**
     * Returns a list of Objects of Class T from persistence using a raw sql statement.
     *
     * @param classType Class type to find
     * @param sqlQuery  raw sql statement
     */
    public <T> List<T> queryObjects(Class<T> classType, String sqlQuery) throws DBException {
        try {
            Query query = em.createNativeQuery(sqlQuery, classType);

            //noinspection unchecked
            return query.getResultList();
        } catch (Exception e) {
            throw new DBException(e.getMessage());
        }
    }

    public <T> List<T> executeQuery(String queryString, List<Object> bindVariables) throws DBException {
        int offset = 0;
        int limit = 0;

        return getQueryResults(getJPAQuery(em, queryString, bindVariables), offset, limit);
    }

    // Private helper method that checks that the database is in a valid
    // state to execute against.  Throws an exception if it is not.
    protected void checkDatabase() throws DBException {
        if (em == null || !em.isOpen()) {
            jgLog.error("em: " + em);
            jgLog.error("em.isOpen()? " + em.isOpen());
            throw new DBException("Database is closed");
        }
        if (!transaction.isActive()) {
            jgLog.debug("Starting transaction");
            transaction.begin();
        }
    }
}
