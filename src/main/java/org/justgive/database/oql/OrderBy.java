package org.justgive.database.oql;

import org.justgive.database.DatabaseItem;
import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;

/**
 */
public class OrderBy<T extends DatabaseItem> {
    private static Logger jgLog = LoggerFactory.getLogger(OrderBy.class);

    private OqlQuery<T> dbItemQuery;
    private String[] fieldNames;
    private String order;

    OrderBy(OqlQuery<T> dbItemQuery) {
        this.dbItemQuery = dbItemQuery;
    }

    OrderBy<T> orderBy(String... fieldNames) {
        if (fieldNames == null || fieldNames.length == 0) {
            throw new IllegalArgumentException("fieldNames must not be null");
        }
        this.fieldNames = fieldNames;
        return this;
    }

    public OqlQuery<T> ascending() {
        this.order = "ASC";
        return dbItemQuery;
    }

    public OqlQuery<T> descending() {
        this.order = "DESC";
        return dbItemQuery;
    }

    @Override
    public String toString() {
        StringBuilder orderByString = new StringBuilder(" ORDER BY ");

        int count = 0;
        for (String fieldName : fieldNames) {
            if (++count != 1) orderByString.append(", ");
            orderByString.append(fieldName);
        }

        orderByString.append(" ").append(order);

        jgLog.trace("orderByString: " + orderByString);

        return orderByString.toString();
    }
}
