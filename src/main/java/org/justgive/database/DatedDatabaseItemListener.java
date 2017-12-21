package org.justgive.database;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 */
public class DatedDatabaseItemListener {
	@PrePersist
	public void createTimestamps(Object o) {
		if (o instanceof DatedDatabaseItem) {
			if (o instanceof DatedDatabaseItem) {
				DatedDatabaseItem item = (DatedDatabaseItem) o;

				item.setDateCreated();
				item.setLastUpdated();
			}
		}
	}

	@PreUpdate
	public void updateTimestamp(Object o) {
		if (o instanceof DatedDatabaseItem) {
			DatedDatabaseItem item = (DatedDatabaseItem) o;

			item.setLastUpdated();
		}
	}
}
