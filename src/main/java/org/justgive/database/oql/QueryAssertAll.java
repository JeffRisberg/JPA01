package org.justgive.database.oql;

import org.justgive.database.DatabaseItem;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class QueryAssertAll<T extends DatabaseItem> extends AssertAll<OqlQuery<T>> {
    private boolean whereAll = false;

    protected QueryAssertAll(OqlQuery<T> query) {
        super(query);
    }

    private QueryAssertAll<T> where(List<String> fieldNames) {
        List<String> subList = new ArrayList<String>(fieldNames);
        clause.where(subList.remove(0));
        whereAll = true;
        super.and(subList);
        return this;
    }

    private void where(String fieldName) {
        clause.appendFieldOperator("WHERE", fieldName);
    }

    @Override
    QueryAssertAll<T> and(List<String> fieldNames) {
        if (clause.isEmpty()) return where(fieldNames);

        super.and(fieldNames);
        return this;
    }

    @Override
    public OqlQuery<T> equalsAll(List<Object> fieldValues) {
        if (whereAll) {
            return whereAll(fieldValues);
        } else {
            return super.equalsAll(fieldValues);
        }
    }

    private OqlQuery<T> whereAll(List<Object> fieldValues) {
        List<Object> subList = new ArrayList<Object>(fieldValues);
        equalTo(subList.remove(0));
        whereAll = false;
        return super.equalsAll(subList);
    }
}
