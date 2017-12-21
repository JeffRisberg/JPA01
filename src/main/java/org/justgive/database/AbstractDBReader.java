package org.justgive.database;

import java.util.ArrayList;
import java.util.List;

/**
 * The AbstractDBReader class helps in defining DBReader objects for specific db technologies
 * (such as JPA).
 *
 * @author Jeff Risberg
 * @since 10/20/14
 */
public abstract class AbstractDBReader implements DBReader {

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
        // detach
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
    public <T> List<T> getObjects(Class<T> classObject, String queryString) throws DBException {
        List<T> result = getObjects(classObject, queryString, null);
        // detach
        return result;
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
        // detach
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
        List<Object> bindVariables = new ArrayList<Object>();
        bindVariables.add(bindVariable);

        List<T> result = getObjects(classObject, queryString, bindVariables);
        // detach
        return result;
    }

    /**
     * Returns an object for a query string.
     *
     * @param classObject   type of Object to return
     * @param queryString   The SQL query string to execute.
     * @param bindVariables A List of bind variable values associated
     *                      with the query string.
     * @return The first object in the query's result set
     * @throws org.justgive.database.DBException on any exception.
     */
    public <T> T getObject(Class<T> classObject, String queryString, List bindVariables) throws DBException {
        List<T> returnObjects = getObjects(classObject, queryString, bindVariables);

        T returnObject = null;
        if (returnObjects.size() > 0) {
            returnObject = returnObjects.get(0);
        }
        // detach
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
    public abstract <T> List<T> getObjects(Class<T> classObject, String queryString, List bindVariables) throws DBException;

    /**
     * TODO - test this method!
     * Returns a single Object of Class T from persistence using a raw sql statement.
     *
     * @param classType Class type to find
     * @param sqlQuery  raw sql statement
     * @return instance of class type T
     * @throws org.justgive.database.DBException if there is a problem with the statement, or connecting to the db
     */
    public <T> T queryObject(Class<T> classType, String sqlQuery) throws DBException {
        T result = getObject(classType, getNativeSqlQuery(classType, sqlQuery), null);
        // detach
        return result;
    }

    /**
     * TODO - test this method!
     * Returns a list of Objects of Class T from persistence using a raw sql statement.
     *
     * @param classType Class type to find
     * @param sqlQuery  raw sql statement
     * @return instance of class type T
     * @throws org.justgive.database.DBException if there is a problem with the statement, or connecting to the db
     */
    public <T> List<T> queryObjects(Class<T> classType, String sqlQuery) throws DBException {
        List<T> result = getObjects(classType, getNativeSqlQuery(classType, sqlQuery), null);
        // detach
        return result;
    }

    protected abstract String getNativeSqlQuery(Class classObject, String sqlQuery) throws DBException;
}
