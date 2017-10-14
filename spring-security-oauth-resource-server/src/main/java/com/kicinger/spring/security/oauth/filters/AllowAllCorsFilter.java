package com.kicinger.spring.security.oauth.filters;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class servers as a filter that adds CORS headers.
 *
 * When the client from another domain requests authorization server endpoints it first sends HTTP OPTIONS
 * method that checks if main request can be performed.
 *
 * This class has the highest precedence so this will be the first filter that checks the request.
 *
 * Problem found when connecting AngularJS application.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AllowAllCorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Method", "POST, PUT, GET, OPTIONS, DELETE");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpStatus.OK.value());
        } else {
            chain.doFilter(request, httpResponse);
        }
    }

    @Override
    public void destroy() {}

}
