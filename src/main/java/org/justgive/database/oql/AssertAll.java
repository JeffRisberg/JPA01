package org.justgive.database.oql;

import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Donor: peter
 * Date: 3/18/13
 * Time: 11:40 AM
 */
public class AssertAll<T extends QqlClause> {
    protected T clause;

    protected List<String> equalsAllFields = Collections.EMPTY_LIST;

    protected AssertAll(T clause) {
        this.clause = clause;
    }

    AssertAll<T> and(List<String> fieldNames) {
        this.equalsAllFields = fieldNames;
        return this;
    }

    public T equalsAll(List<Object> fieldValues) {
        if (fieldValues.size() != equalsAllFields.size()) return clause;
        for (int i = 0; i < fieldValues.size(); i++) {
            and(equalsAllFields.get(i));
            equalTo(fieldValues.get(i));
        }
        equalsAllFields = Collections.EMPTY_LIST;
        return clause;
    }

    protected void equalTo(Object fieldValue) {
        clause.compareTo("=", fieldValue);
    }

    private void and(String fieldName) {
        clause.appendFieldOperator("AND", fieldName);
    }
}
