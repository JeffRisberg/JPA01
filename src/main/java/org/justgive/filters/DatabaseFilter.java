package org.justgive.filters;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.*;
import java.io.IOException;

/**
 * The <i>DatabaseFilter</i> class...
 *
 * @author Jeff Risberg
 * @since early June 2014
 */
public class DatabaseFilter implements Filter {
    private static Logger jgLog = LoggerFactory.getLogger(DatabaseFilter.class);

    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction tx;

    //private SessionFactory sf;
    public static final ThreadLocal<EntityManager>
            ENTITY_MANAGERS = new ThreadLocal<EntityManager>();

    public void init(FilterConfig filterConfig) throws ServletException {
        jgLog.debug("Initializing filter...");
        jgLog.debug("Obtaining EntityManagerFactory");

        emf = Persistence.createEntityManagerFactory("justgive");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            jgLog.debug("Starting a database transaction");
            //sf.getCurrentSession().beginTransaction();

            em = emf.createEntityManager();
            ENTITY_MANAGERS.set(em);

            tx = em.getTransaction();
            tx.begin();

            jgLog.debug(em);

            // Call the next filter (continue request processing)
            chain.doFilter(request, response);

            // Commit and cleanup
            jgLog.debug("Committing the database transaction");
            //sf.getCurrentSession().getTransaction().commit();
            if (tx.isActive()) {
                tx.commit();
            }
            em.close();
        } catch (Throwable ex) {
            // Rollback only
            ex.printStackTrace();
            //try {
            //    if (sf.getCurrentSession().getTransaction().isActive()) {
            //        log.debug("Trying to rollback database transaction after exception");
            //        sf.getCurrentSession().getTransaction().rollback();
            //    }
            //} catch (Throwable rbEx) {
            //    log.error("Could not rollback transaction after exception!", rbEx);
            //}

            // Let others handle it... maybe another interceptor for exceptions?
            throw new ServletException(ex);
        }
    }

    public void destroy() {
        jgLog.debug("destroy emf");
        if (emf != null) {
            emf.close();
        }
    }
}