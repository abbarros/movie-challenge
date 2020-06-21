package br.com.abbarros.moviechallenge.config.mdc;

import org.jboss.logging.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
public class MDCConfig implements Filter {

    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String correlationId = ((HttpServletRequest) servletRequest).getHeader("correlationId");
        if(Objects.isNull(correlationId))
            correlationId = UUID.randomUUID().toString();

        MDC.put("correlationId", correlationId);

        filterChain.doFilter(servletRequest, servletResponse);
    }

}