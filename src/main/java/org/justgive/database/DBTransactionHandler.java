package org.justgive.database;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;

/**
 * DBTransactionHandler is a convenience class to wrap a Database related actions with
 * boilerplate DBTransaction open/close/cleanup code.
 */
public abstract class DBTransactionHandler<T> {
	private static Logger jgLog = LoggerFactory.getLogger(DBTransactionHandler.class);

	private boolean commitOnException = false;

	protected DBTransactionHandler() throws DBException {}

	public DBTransactionHandler<T> commitOnException() {
		this.commitOnException = true;
		return this;
	}

	public T execute() throws DBException {
		T type;
		DBTransaction transaction = null;
		try {
			transaction = DBSessionFactory.getInstance().getTransaction();
			transaction.begin();

			type = handleTransaction();

			transaction.commit();
			jgLog.debug("DBTransactionHandler.commit()");
		} catch (Throwable e) {
			jgLog.error(e);
			throw new DBException(e.getMessage());
		} finally {
			if (transaction != null) {
				if (transaction.isActive()) {
					if (commitOnException) {
						jgLog.debug("DBTransactionHandler.commit()");
						transaction.commit();
					} else {
						jgLog.debug("DBTransactionHandler.rollback()");
						transaction.rollback();
					}
				}
				//start a new transaction so subsequent object changes
				//get registered
				transaction.begin();
			}
		}
		return type;
	}

	protected abstract T handleTransaction() throws Exception;

}

