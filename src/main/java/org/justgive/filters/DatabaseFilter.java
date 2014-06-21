package org.justgive.filters;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;

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

    //@PersistenceUnit
    //private EntityManagerFactory emf;

    //private SessionFactory sf;

    public void init(FilterConfig filterConfig) throws ServletException {
        jgLog.debug("Initializing filter...");
        jgLog.debug("Obtaining SessionFactory from static HibernateUtil singleton");
        //sf = HibernateUtil.getSessionFactory();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            jgLog.debug("Starting a database transaction");
            //sf.getCurrentSession().beginTransaction();

            //EntityManager em = emf.createEntityManager();

            // Call the next filter (continue request processing)
            chain.doFilter(request, response);

            // Commit and cleanup
            jgLog.debug("Committing the database transaction");
            //sf.getCurrentSession().getTransaction().commit();
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
    }
}