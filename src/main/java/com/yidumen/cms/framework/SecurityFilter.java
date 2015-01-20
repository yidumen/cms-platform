package com.yidumen.cms.framework;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 蔡迪旻
 */
@WebFilter(filterName = "SecurityFilter", servletNames = {"default"})
public class SecurityFilter implements Filter {

    private FilterConfig filterConfig = null;

    public SecurityFilter() {
    }


    /**
     * Return the filter configuration object for this filter.
     *
     * @return
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    /**
     * Init method for this filter
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("SecurityFilter()");
        }
        StringBuilder sb = new StringBuilder("SecurityFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            final HttpServletRequest hsr = (HttpServletRequest) request;
            final String uri = hsr.getRequestURI();
            if (uri.startsWith("/resource") || uri.equals("/") || uri.startsWith("/login")) {
                fc.doFilter(request, response);
                return;
            }
            final HttpSession session = hsr.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                final HttpServletResponse hResponse = (HttpServletResponse) response;
                hResponse.sendRedirect("/");
            } else {
                fc.doFilter(request, response);
            }
        }
    }

}
