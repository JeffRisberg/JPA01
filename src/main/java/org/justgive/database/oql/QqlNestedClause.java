package org.justgive.database.oql;

/**
 */
public class QqlNestedClause extends QqlClause {
    public QqlNestedClause() {}

    @Override
    public String toString() {
        return "(" + super.toString() + ")";
    }
}
