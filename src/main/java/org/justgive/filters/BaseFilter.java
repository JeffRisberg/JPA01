package org.justgive.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * <code>BaseFilter</code> provides a simple implementation of Filter so subclasses can avoid lots of boilerplate code.
 */
public abstract class BaseFilter implements Filter {
    protected FilterConfig filterConfig = null;

    public void init(FilterConfig config) {
        this.filterConfig = config;
    }

    public void destroy() {
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public FilterConfig getFilterConfig() {
        return this.filterConfig;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        if (filterConfig == null) {
            return;
        }
    }
}
