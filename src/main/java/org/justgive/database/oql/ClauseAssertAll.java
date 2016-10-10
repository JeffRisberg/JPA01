package org.justgive.database.oql;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Donor: peter
 * Date: 3/18/13
 * Time: 11:45 AM
 */
public class ClauseAssertAll extends AssertAll<QqlClause> {
    ClauseAssertAll(QqlClause dbItemClause) {
        super(dbItemClause);
    }

    @Override
    ClauseAssertAll and(List<String> fieldNames) {
        return (ClauseAssertAll) super.and(fieldNames);
    }
}
