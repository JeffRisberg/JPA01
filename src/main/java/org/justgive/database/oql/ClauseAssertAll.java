package org.justgive.database.oql;

import java.util.List;

/**
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
