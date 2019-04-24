package com.company.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * Hibernate session management utils
 *
 * Singleton that initializes the session factory and facilitates use of a session per tenant
 */
public final class DBSessionManager {

  private static final Logger logger = LoggerFactory.getLogger(DBSessionManager.class);
  private static DBSessionManager MANAGEMENT_INSTANCE = null;
  private static DBSessionManager TENANT_INSTANCE = null;

  private final SessionFactory sessionFactory;

  private DBSessionManager(Configuration configuration) {
    this.sessionFactory = configuration.buildSessionFactory();
  }

  public static DBSessionManager getInstance(Configuration configuration) {
    return getInstance(configuration, true);
  }

  public static DBSessionManager getInstance(Configuration configuration, boolean tenant) {
    if (tenant) {
      return tenantInstance(configuration);
    }
    return managementInstance(configuration);
  }

  public Session openSession(String namespace) {
    return this.sessionFactory.withOptions().tenantIdentifier(namespace).openSession();
  }

  /**
   * Think about keeping session across different calls
   * Keeping sessions helps caching of objects and reduce the calls to the database for reads
   *
   * @param namespace
   * @param consumer
   */
  public void useSession(String namespace, Consumer<Session> consumer) {
    try {
      this.useSessionUnsafe(namespace, consumer);
    } catch (Throwable e) {
      logger.error("Failed to execute transaction", e);
    }
  }

  public void useSessionUnsafe(String namespace, Consumer<Session> consumer) {
    try (Session session = openSession(namespace)) {
      Transaction transaction = session.beginTransaction();
      try {
        consumer.accept(session);
        transaction.commit();
      } catch (Throwable e) {
        transaction.rollback();
        logger.error("Failed to execute transaction", e);
        throw e;
      }
    }
  }

  private void close() {
    this.sessionFactory.close();
  }

  private synchronized static DBSessionManager managementInstance(Configuration configuration) {
    if (MANAGEMENT_INSTANCE == null) {
      if (configuration == null) {
        configuration = null;//CommonConfig.createHibernateConfig(CommonConfig.DEFAULT_TENANT_STORE_DB_NAME);
      }
      MANAGEMENT_INSTANCE = new DBSessionManager(configuration);
      Runtime.getRuntime().addShutdownHook(new Thread(MANAGEMENT_INSTANCE::close));
    }
    return MANAGEMENT_INSTANCE;
  }

  private synchronized static DBSessionManager tenantInstance(Configuration configuration) {
    if (TENANT_INSTANCE == null) {
      TENANT_INSTANCE = new DBSessionManager(configuration);
      Runtime.getRuntime().addShutdownHook(new Thread(TENANT_INSTANCE::close));
    }
    return TENANT_INSTANCE;
  }
}
