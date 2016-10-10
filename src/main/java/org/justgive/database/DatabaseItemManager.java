package org.justgive.database;

import org.justgive.database.oql.OqlQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * DatabaseItemManager adds an abstraction layer on top of DBSession to create a simple
 * interface for managing the persistence of all DatabaseItems without having to
 * think about databases connections, transaction, etc. Its core methods are get, save
 * and remove and by default it should be used as a Singleton by calling getInstance().
 * This instance uses the DBTransaction from DBSessionFactory.getDBTransaction() that
 * associates one transaction per request.
 * <p/>
 * DatabaseItemManager can also be instantiated to use DBSessionFactory.getDBSession()
 * via getSessionInstance(), or a constructor that accepts a DBSession or DBTransaction
 * as an argument, which overrides the default DBTransaction. In this situation the
 * DBTransaction must be handled outside the DatabaseItemManager. These should only be used in
 * special occasions where an atomic action or small sequence of actions needs to be
 * executed and committed in a self contained method.
 *
 * @author curtis
 * @since 2008
 */
public class DatabaseItemManager {
    private static Logger jgLog = LoggerFactory.getLogger(DatabaseItemManager.class);

    static int TRANSACTION = 1;
    static int CLIENT_MANAGED = 2;

    private static DatabaseItemManager instance;

    private DBTransaction dbTransaction;
    private int dbConnectionType = TRANSACTION;

    protected DatabaseItemManager(int connectionType) {
        this.dbConnectionType = connectionType;
    }

    /**
     * Create an instance of DatabaseItemManager that contains a
     * connection that is managed by the client class. Use this
     * constructor sparingly.
     */
    public DatabaseItemManager(DBTransaction transaction) {
        this(CLIENT_MANAGED);
        this.dbTransaction = transaction;
    }

    /**
     * Defaults to using the standard DBTransaction from
     * DBSessionFactory.getTransaction()
     *
     * @return instance of DatabaseItemManager
     * @throws DBException if DBSessionFactory cannot be accessed
     */
    public static synchronized DatabaseItemManager getInstance() throws DBException {
        if (instance == null) {
            instance = new DatabaseItemManager(TRANSACTION);
        }
        return instance;
    }

    /**
     * Load a single Object instance of Class T from persistence by its unique id.
     *
     * @param classType Class type to find
     * @param id        unique integer identity
     * @return instance of class type T
     * @throws DBException if id can't be found
     */
    public <T extends DatabaseItem> T find(Class<T> classType, int id) throws DBException {
        return getDatabase().load(classType, id);
    }

    /**
     * Load a List of Object instances of Class T from persistence.
     *
     * @param classType Class type to find
     * @return instance of class type T
     * @throws DBException if id can't be found
     */
    public <T extends DatabaseItem> List<T> findAll(Class<T> classType) throws DBException {
        return findAll(new OqlQuery<T>(classType));
    }

    /**
     * Load a List of Object instances of Class T from persistence with (offset,limit).
     *
     * @param classType Class type to find
     * @param offset    starting offset
     * @param limit     max number of rows to return
     * @return instance of class type T
     * @throws DBException if id can't be found
     */
    public <T extends DatabaseItem> List<T> findAll(Class<T> classType, int offset, int limit) throws DBException {
        OqlQuery oqlQuery = new OqlQuery<T>(classType);
        oqlQuery.orderBy("id", "desc");
        return findAll(oqlQuery, offset, limit);
    }

    /**
     * Load a single Object instance of Class T from persistence by a field
     * and value pair, if there are multiple results on the first is returned.
     *
     * @param classType Class type to find
     * @param field     field name to search on
     * @param value     value of field name to search for
     * @return instance of class type T
     * @throws DBException if id can't be found
     */
    public <T extends DatabaseItem> T find(Class<T> classType, String field, Object value) throws DBException {
        return (T) find(new OqlQuery<T>(classType).where(field).equalTo(value));
    }

    /**
     * Load a single Object instance of DatabaseItem Class T from
     * persistence using a field and value pair, if there are multiple results
     * on the first is returned.
     *
     * @param classType Class type to find
     * @param fields    list of field names to search on
     * @param values    list of values of field names to search for
     * @return instance of class type T
     * @throws DBException if id can't be found
     */
    public <T extends DatabaseItem> T find(Class<T> classType, List<String> fields, List values) throws DBException {
        return (T) find(new OqlQuery<T>(classType).whereAll(fields).equalsAll(values));
    }

    /**
     * Load a list of DatabaseItems of Class T from persistence by a field
     * and value pair.
     *
     * @param classType Class type to find
     * @param field     field name to search on
     * @param value     value of field name to search for
     * @return instance of class type T
     * @throws DBException if id can't be found
     */
    public <T extends DatabaseItem> List<T> findAll(Class<T> classType, String field, Object value) throws DBException {
        return findAll(classType, Arrays.asList(field), Arrays.asList(value));
    }

    /**
     * Load a list of DatabaseItems of Class T from persistence by a field
     * and value pair.
     *
     * @param classType Class type to find
     * @param fields    list of field names to search on
     * @param values    list of values of field names to search for
     * @return instance of class type T
     * @throws DBException if id can't be found
     */
    public <T extends DatabaseItem> List<T> findAll(Class<T> classType, List<String> fields, List values) throws DBException {
        return findAll(new OqlQuery<T>(classType).whereAll(fields).equalsAll(values));
    }

    /**
     * Load a list of DatabaseItems of Class T from persistence by a field
     * and value pair.
     *
     * @param query DBItemQuery that contains fields and values
     * @return instance of class type T
     * @throws DBException if id can't be found
     */
    public <T extends DatabaseItem> List<T> findAll(OqlQuery<T> query) throws DBException {
        return getDatabase().getObjects(query.getQueryClass(), query.toString(), query.getArgumentValues());
    }

    /**
     * Load a list of DatabaseItems of Class T from persistence by a field
     * and value pair.
     *
     * @param query  DBItemQuery that contains fields and values
     * @param offset starting offset
     * @param limit  max number of rows to return
     * @return instance of class type T
     * @throws DBException if id can't be found
     */
    public <T extends DatabaseItem> List<T> findAll(OqlQuery<T> query, int offset, int limit) throws DBException {
        return getDatabase().getObjects(query.getQueryClass(), query.toString(), query.getArgumentValues(), offset, limit);
    }

    /**
     * Load a list of DatabaseItems of Class T from persistence by a field
     * and value pair.
     *
     * @param classType Class type to find
     * @param offset starting offset
     * @param limit  max number of rows to return
     * @return instance of class type T
     * @throws DBException if id can't be found
     */
    public <T extends DatabaseItem> List<T> findAll(Class<T> classType, List<String> fields, List values, int offset, int limit) throws DBException {
        return findAll(new OqlQuery<T>(classType).whereAll(fields).equalsAll(values), offset, limit);
    }

    /**
     * Load a list of DatabaseItems of Class T from persistence by a field
     * and value pair.
     *
     * @param classType Class type to find
     * @param offset starting offset
     * @param limit  max number of rows to return
     * @return instance of class type T
     * @throws DBException if id can't be found
     */
    public <T extends DatabaseItem> List<T> findAll(Class<T> classType, String field, Object value, int offset, int limit) throws DBException {
        return findAll(classType, Arrays.asList(field), Arrays.asList(value), offset, limit);
    }

    public <T extends DatabaseItem> T find(OqlQuery<T> query) throws DBException {
        return getDatabase().getObject(query.getQueryClass(), query.toString(), query.getArgumentValues());
    }

    public DBTransaction getDatabase() throws DBException {
        if (dbConnectionType == TRANSACTION) {
            // start a transaction here
            return DBSessionFactory.getInstance().getTransaction();
        } else {
            return dbTransaction;
        }
    }
}
