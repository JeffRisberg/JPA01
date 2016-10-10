package org.justgive.database.oql;

import org.justgive.database.DatabaseItem;

/**
 * Created by IntelliJ IDEA.
 * Donor: peter
 * Date: 3/15/13
 * Time: 12:15 PM
 */
public class QueryAssert<T extends DatabaseItem> extends ClauseAssert {
    QueryAssert(OqlQuery<T> dbItemClause) {
        super(dbItemClause);
    }

    @Override
    QueryAssert<T> where(String fieldName) {
        return and(fieldName);
    }

    @Override
    QueryAssert<T> and(String fieldName) {
        if (clause.isEmpty()) return appendWhere(fieldName);

        super.and(fieldName);
        return this;
    }

    private QueryAssert<T> appendWhere(String fieldName) {
        fieldOperator("WHERE", fieldName);
        return this;
    }

    @Override
    QueryAssert<T> or(String fieldName) {
        if (clause.isEmpty()) return where(fieldName);
        
        super.or(fieldName);
        return this;
    }

    @Override
    public OqlQuery<T> compareTo(String comparator, Object fieldValue) {
        return (OqlQuery<T>) super.compareTo(comparator, fieldValue);
    }

    @Override
    public OqlQuery<T> equalTo(Object fieldValue) {
        return (OqlQuery<T>) super.equalTo(fieldValue);
    }

    @Override
    public OqlQuery<T> notEqualTo(Object fieldValue) {
        return (OqlQuery<T>) super.notEqualTo(fieldValue);
    }

    @Override
    public OqlQuery<T> lessThan(Object fieldValue) {
        return (OqlQuery<T>) super.lessThan(fieldValue);
    }

    @Override
    public OqlQuery<T> lessThanOrEqualTo(Object fieldValue) {
        return (OqlQuery<T>) super.lessThanOrEqualTo(fieldValue);
    }

    @Override
    public OqlQuery<T> greaterThan(Object fieldValue) {
        return (OqlQuery<T>) super.greaterThan(fieldValue);
    }

    @Override
    public OqlQuery<T> greaterThanOrEqualTo(Object fieldValue) {
        return (OqlQuery<T>) super.greaterThanOrEqualTo(fieldValue);
    }

    @Override
    public OqlQuery<T> like(Object fieldValue) {
        return (OqlQuery<T>) super.like(fieldValue);
    }

    @Override
    public OqlQuery<T> between(Object fieldValue1, Object fieldValue2) {
        return (OqlQuery<T>) super.between(fieldValue1, fieldValue2);
    }

    @Override
    public OqlQuery<T> in(Object... fieldValues) {
        return (OqlQuery<T>) super.in(fieldValues);
    }
}
