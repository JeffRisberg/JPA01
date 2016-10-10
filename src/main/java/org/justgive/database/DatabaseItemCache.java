package org.justgive.database;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The <i>DatabaseItemCache</i> can be used to cache the ids of database records so that a search by a specific
 * key value can be converted to the primary key of the record.  The reason is that database fetches by primary
 * key are cached by EhCache, which saves a database fetch.
 *
 * @author Jeff Risberg, Peter Cowan
 * @since late November 2015
 */
public class DatabaseItemCache<T extends DatabaseItem> {
    private static Logger jgLog = LoggerFactory.getLogger(DatabaseItemCache.class);

    protected Class<T> classType;
    protected String keyField;
    private int maxEntries;

    private LinkedHashMap<String, Integer> idCache;

    /**
     * Constructor
     */
    public DatabaseItemCache(Class<T> classType, String keyField, final int maxEntries) {
        this.classType = classType;
        this.keyField = keyField;
        this.maxEntries = maxEntries;

        // This is based on an LRU cache example from http://stackoverflow.com/questions/224868/easy-simple-to-use-lru-cache-in-java
        this.idCache = new LinkedHashMap<String, Integer>(maxEntries + 1, .75F, true) {
            // This method is called just after a new entry has been added
            public boolean removeEldestEntry(Map.Entry eldest) {
                return size() > maxEntries;
            }
        };
    }

    public T getItem(String key) throws DBException {
        if (idCache.containsKey(key)) {
            Integer itemId = idCache.get(key);
            if (itemId == -1) return null;

            jgLog.debug("Loading " + classType.getSimpleName() + " by cached Id: " + itemId);
            return (T) DatabaseItemManager.getInstance().find(classType, itemId);
        } else {
            jgLog.debug("Loading " + classType.getSimpleName() + " by " + keyField + " " + key);
            T item = findItemByKey(key);
            addItem(key, item);
            return item;
        }
    }

    protected T findItemByKey(String key) throws DBException {
        return (T) DatabaseItemManager.getInstance().find(classType, keyField, key);
    }

    public void addItem(String key, T item) {
        Integer itemId = item != null ? item.getId() : new Integer(-1);
        jgLog.debug("Adding " + classType.getSimpleName() + " with id " + itemId + " and " + keyField + " with value " + key + " to cache");
        idCache.put(key, itemId);
        jgLog.debug("Cache contains " + idCache.size() + " entries of " + maxEntries + " total capacity");
    }

    public void flushItem(String key) {
        if (idCache.containsKey(key)) {
            idCache.remove(key);
        }
    }
}