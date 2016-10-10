package org.justgive.database.oql;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;
import org.justgive.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Donor: peter
 * Date: 3/13/13
 * Time: 4:46 PM
 */
public class QqlClause {
    private static Logger jgLog = LoggerFactory.getLogger(QqlClause.class);
    public static final String ordinalToken = "?";

    protected StringBuffer clauseString = new StringBuffer();
    private int argumentCount = 0;
    private List<String> argumentFields = new ArrayList();
    private List argumentValues = new ArrayList();

    public QqlClause() {
    }

    public QqlClause where(QqlClause clause) {
        if (isEmpty()) appendClauseOperator("", clause);
        else and(clause);
        return this;
    }

    public boolean isEmpty() {
        return clauseString.length() == 0;
    }

    protected QqlClause appendClauseOperator(String andOr, QqlClause clause) {
        appendOperator(andOr, clause.toString() + " ");
        addClauseValues(clause);
        return this;
    }

    private QqlClause appendOperator(String andOr, String fieldName) {
        if (argumentCount > 0) clauseString.append(" ");
        clauseString.append(andOr);
        if (!StringUtil.isBlank(andOr)) clauseString.append(" ");
        clauseString.append(fieldName);
        return this;
    }

    protected void addClauseValues(QqlClause clause) {
        for (String field : clause.getArgumentFields()) {
            addArgumentField(field);
        }

        for (Object value : clause.getArgumentValues()) {
            addArgumentValue(value);
            incrementCount();
        }
    }

    protected void addArgumentField(String fieldName) {
        argumentFields.add(fieldName);
    }

    protected void addArgumentValue(Object fieldValue) {
        argumentValues.add(fieldValue);
    }

    protected int incrementCount() {
        return ++argumentCount;
    }

    public QqlClause and(QqlClause clause) {
        if (isEmpty()) where(clause);
        else appendClauseOperator("AND", clause);
        return this;
    }

    public QqlClause or(QqlClause clause) {
        if (isEmpty()) where(clause);
        else appendClauseOperator("OR", clause);
        return this;
    }

    public ClauseAssert where(String fieldName) {
        return new ClauseAssert(this).where(fieldName);
    }

    public AssertAll whereAll(List<String> fieldNames) {
        return andAll(fieldNames);
    }

    public ClauseAssert and(String fieldName) {
        return new ClauseAssert(this).and(fieldName);
    }

    public AssertAll andAll(List<String> fieldNames) {
        return new AssertAll(this).and(fieldNames);
    }

    public ClauseAssert or(String fieldName) {
        return new ClauseAssert(this).or(fieldName);
    }

    public int getArgumentCount() {
        return argumentCount;
    }

    public List<String> getArgumentFields() {
        return argumentFields;
    }

    public List getArgumentValues() {
        return argumentValues;
    }

    @Override
    public String toString() {
        printClause(clauseString.toString());
        return clauseString.toString();
    }

    protected void printClause(String clause) {
        StringBuffer debugQueryStr = new StringBuffer(clause);

        int totalArgs = argumentValues.size();
        for (int i = 0; i < totalArgs; i++) {
            if (argumentFields.size() > i && argumentValues.size() > i) {
                jgLog.trace(argumentFields.get(i) + ": " + argumentValues.get(i));
            } else {
                jgLog.trace("Argument Field/Value mismatch");
            }
            if (jgLog.isDebugEnabled()) {
                String token = ordinalToken + (i + 1);
                int tokenIndex = debugQueryStr.indexOf(token);
                if (tokenIndex >= 0) {
                    debugQueryStr.replace(tokenIndex, tokenIndex + token.length(), "'" + argumentValues.get(i) + "'");
                }
            }
        }
        //jgLog.debug("Argument Count: " + (argumentCount - 1));
        //jgLog.debug("Total ArgumentValues: " + argumentValues.size());
        //jgLog.debug("Debug Query: " + debugQueryStr);
        jgLog.debug("Actual Query: " + clause);
    }

    QqlClause appendFieldOperator(String andOr, String fieldName) {
        appendOperator(andOr, fieldName);
        addArgumentField(fieldName);
        return this;
    }

    QqlClause compareTo(String comparator, Object fieldValue) {
        clauseString.append(" ").append(comparator).append(" " + ordinalToken).append(incrementCount());
        addArgumentValue(fieldValue);
        return this;
    }

    QqlClause between(Object fieldValue1, Object fieldValue2) {
        clauseString.append(" BETWEEN " + ordinalToken).append(incrementCount())
                .append(" AND " + ordinalToken).append(incrementCount());
        addArgumentField("IGNORE");
        addArgumentValue(fieldValue1);
        addArgumentValue(fieldValue2);
        return this;
    }

    QqlClause in(Object... fieldValues) {
        clauseString.append(" IN LIST (");

        int count = 0;
        for (Object fieldValue : fieldValues) {
            if (++count != 1) {
                clauseString.append(", ");
            }
            clauseString.append(ordinalToken).append(getBindKey(fieldValue));
            addArgumentValue(getBindValue(fieldValue));
        }
        clauseString.append(") ");
        return this;
    }

    private String getBindKey(Object fieldValue) {
        return String.valueOf(incrementCount());
    }

    private Object getBindValue(Object fieldValue) {
        if (fieldValue == null) return "nil";
        return fieldValue;
    }
}
