package org.justgive.database.oql;

/**
 * Created by IntelliJ IDEA.
 * Donor: peter
 * Date: 3/15/13
 * Time: 11:15 AM
 */
public class ClauseAssert {
    protected QqlClause clause;

    ClauseAssert(QqlClause clause) {
        this.clause = clause;
    }

    ClauseAssert where(String fieldName) {
        return and(fieldName);
    }

    ClauseAssert and(String fieldName) {
        return (clause.isEmpty()) ? fieldOperator("", fieldName) : fieldOperator("AND", fieldName);
    }

    ClauseAssert or(String fieldName) {
        return (clause.isEmpty()) ? fieldOperator("", fieldName) : fieldOperator("OR", fieldName);
    }

    protected ClauseAssert fieldOperator(String andOr, String fieldName) {
        clause.appendFieldOperator(andOr, fieldName);
        return this;
    }

    public QqlClause compareTo(String comparator, Object fieldValue) {
        clause.compareTo(comparator, fieldValue);
        return clause;
    }

    public QqlClause equalTo(Object fieldValue) {
        return compareTo("=", fieldValue);
    }

    public QqlClause notEqualTo(Object fieldValue) {
        return compareTo("!=", fieldValue);
    }

    public QqlClause lessThan(Object fieldValue) {
        return compareTo("<", fieldValue);
    }

    public QqlClause lessThanOrEqualTo(Object fieldValue) {
        return compareTo("<=", fieldValue);
    }

    public QqlClause greaterThan(Object fieldValue) {
        return compareTo(">", fieldValue);
    }

    public QqlClause greaterThanOrEqualTo(Object fieldValue) {
        return compareTo(">=", fieldValue);
    }

    public QqlClause like(Object fieldValue) {
        return compareTo("LIKE", fieldValue);
    }

    public QqlClause between(Object fieldValue1, Object fieldValue2) {
        clause.between(fieldValue1, fieldValue2);
        return clause;
    }

    public QqlClause in(Object... fieldValues) {
        clause.in(fieldValues);
        return clause;
    }
}
