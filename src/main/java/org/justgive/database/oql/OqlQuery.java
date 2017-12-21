package org.justgive.database.oql;

import org.justgive.database.DatabaseItem;
import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;

import java.util.List;

/**
 */
public class OqlQuery<T extends DatabaseItem> extends QqlClause {
    private static Logger jgLog = LoggerFactory.getLogger(OqlQuery.class);

    private Class<T> queryClass;
    private String aggregateFunction;
    private Integer offset;
    private Integer limit;
    private OrderBy orderBy;

    public OqlQuery() {}

    public OqlQuery(Class<T> queryClass) {
        if (queryClass == null) {
            throw new IllegalArgumentException("Null queryClass");
        }
        this.queryClass = queryClass;
    }

    public Class<T> getQueryClass() {
        return queryClass;
    }

    private OqlQuery(Class<T> queryClass, String aggregateFunction) {
        this(queryClass);
        if (queryClass == null) {
            throw new IllegalArgumentException("Null queryClass");
        }
        this.aggregateFunction = aggregateFunction;
    }

    public OqlQuery<T> where(QqlClause clause) {
        if (isEmpty()) appendClauseOperator("WHERE", clause);
        else and(clause);
        return this;
    }

    @Override
    public OqlQuery<T> and(QqlClause clause) {
        super.and(clause);
        return this;
    }

    @Override
    public OqlQuery<T> or(QqlClause clause) {
        super.or(clause);
        return this;
    }

    @Override
    public QueryAssert<T> where(String fieldName) {
        return new QueryAssert<T>(this).and(fieldName);
    }

    public QueryAssertAll<T> whereAll(List<String> fieldNames) {
        return andAll(fieldNames);
    }

    @Override
    public QueryAssert and(String fieldName) {
        return new QueryAssert(this).and(fieldName);
    }

    @Override
    public QueryAssertAll<T> andAll(List<String> fieldNames) {
        return new QueryAssertAll(this).and(fieldNames);
    }

    @Override
    public QueryAssert<T> or(String fieldName) {
        return new QueryAssert(this).or(fieldName);
    }

    public OqlQuery<T> offset(int offset) {
        this.offset = offset;
        return this;
    }

    public OqlQuery<T> limit(int limit) {
        this.limit = limit;
        return this;
    }

    public OrderBy<T> orderBy(String fieldName) {
        orderBy = new OrderBy(this).orderBy(fieldName);
        return orderBy;
    }

    public OrderBy<T> orderBy(String fieldName, String direction) {
        orderBy = new OrderBy(this).orderBy(fieldName);

        if (direction != null && direction.equalsIgnoreCase("desc")) orderBy.descending();
        if (direction != null && direction.equalsIgnoreCase("asc")) orderBy.ascending();

        return orderBy;
    }

    @Override
    public String toString() {
        String baseQuery = (aggregateFunction == null) ? generateBaseQuery() : generateAggregateQuery();
        StringBuilder queryString = new StringBuilder(baseQuery);
        queryString.append(" ").append(clauseString);
        if (orderBy != null) queryString.append(orderBy.toString());
        if (limit != null) queryString.append(getLimitString());
        if (offset != null) queryString.append(getOffsetString());
        printClause(queryString.toString());
        return queryString.toString();
    }

    protected String generateBaseQuery() {
        String type = typeName();

        StringBuilder baseQuery = new StringBuilder("SELECT ").append(type)
                .append(" FROM ").append(queryClass.getName()).append(" ").append(type).append(" ");

        jgLog.trace("generateBaseQuery: " + baseQuery.toString());
        return baseQuery.toString();
    }

    private String typeName() {
        String type = queryClass.getSimpleName().substring(0, 1);

        //use camel case
        return type.substring(0, 1).toLowerCase() + type.substring(1, type.length());
    }

    protected String generateAggregateQuery() {
        String type = typeName();

        StringBuilder aggQuery = new StringBuilder("SELECT ").append(aggregateFunction)
                .append(" FROM ").append(queryClass.getName()).append(" ").append(type);

        jgLog.trace("generateAggregateQuery: " + aggQuery);

        return aggQuery.toString();
    }

    private String getOffsetString() {
        StringBuffer offsetClause = new StringBuffer(" :OFFSET:").append(offset).append(":");

        jgLog.trace("offsetClause: " + offsetClause);
        jgLog.trace("queryString: " + clauseString);

        return offsetClause.toString();
    }

    private String getLimitString() {
        StringBuffer limitClause = new StringBuffer(" :LIMIT:").append(limit).append(":");

        jgLog.trace("limit: " + limitClause);
        jgLog.trace("queryString: " + clauseString);

        return limitClause.toString();
    }
}
