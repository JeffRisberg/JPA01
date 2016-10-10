package org.justgive.database;

import java.util.List;

/**
 * The DBReader interface is like DBSession but provides objects for reading, rather than
 * writing back to the database.  Hence, they are detached after being loaded.
 *
 * @author Jeff, Peter
 * @since 10/20/14
 */
public interface DBReader {

    // Returns an object for a given primary key id value
    public <T> T getObjectById(Class<T> classObject, int id) throws DBException;

    //Same as above, but semantically aligned with current application
    public <T> T load(Class<T> classObject, int id) throws DBException;

    // Returns a single object of type T for a query string
    public <T> T getObject(Class<T> classObject, String queryString) throws DBException;

    // Returns a result set of objects of type T for a query string
    public <T> List<T> getObjects(Class<T> classObject, String queryString) throws DBException;

    // Returns a single object of type T for a query string with accompanying bind variables
    public <T> T getObject(Class<T> classObject, String queryString, Object bindVariable) throws DBException;

    // Returns a result set of objects of type T for a query string with accompanying bind variables
    public <T> List<T> getObjects(Class<T> classObject, String queryString, Object bindVariable) throws DBException;

    // Returns a single object of type T for a query string with accompanying bound variables
    public <T> T getObject(Class<T> classObject, String queryString, List bindVariables) throws DBException;

    // Returns a result set of objects of type T for a query string with accompanying bound variables
    public <T> List<T> getObjects(Class<T> classObject, String queryString, List bindVariables) throws DBException;

    // Returns a single Object of Class T from persistence using a raw sql statement.
    public <T> T queryObject(Class<T> classType, String sqlQuery) throws DBException;

    // Returns a list of Objects of Class T from persistence using a raw sql statement.
    public <T> List<T> queryObjects(Class<T> classType, String sqlQuery) throws DBException;

    public <T> List<T> executeQuery(String queryString, List<Object> bindVariables)
            throws DBException;
}
