package coms.kw.ac.kr.server.config.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter(description = "XSS secured filter for HttpServletRequest", urlPatterns = "/*", filterName = "XSSFilter")
public class XSSFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(new XSSFilteredRequestWrapper((HttpServletRequest) request), response);
    }

    @Override
    public void destroy() {
    }
}