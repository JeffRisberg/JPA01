package org.justgive.database.oql;

/**
 * Created by IntelliJ IDEA.
 * Donor: peter
 * Date: 3/14/13
 * Time: 5:09 PM
 */
public class QqlNestedClause extends QqlClause {
    public QqlNestedClause() {}

    @Override
    public String toString() {
        return "(" + super.toString() + ")";
    }
}
