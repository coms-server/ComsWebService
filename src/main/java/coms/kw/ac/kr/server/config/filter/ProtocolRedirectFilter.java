package coms.kw.ac.kr.server.config.filter;

import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Profile("production")
@WebFilter(description = "Redirect(302) response's Protocol Changer", urlPatterns = "/*", filterName = "HttpsRedirectFilter")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ProtocolRedirectFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(request, response);

        int status = ((HttpServletResponse) response).getStatus();
        if (status == HttpStatus.FOUND.value()) {
            String location = ((HttpServletResponse) response).getHeader("Location");

            if (location.startsWith("http:")) {
                String redirect = location.replaceFirst("http", "https");
                ((HttpServletResponse) response).setHeader("Location", redirect);
            }
        }

    }

    @Override
    public void destroy() {
    }
}