package org.aquam.configuration.token;

import org.aquam.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenValidatorFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final HandlerExceptionResolver resolver;

    @Autowired
    public TokenValidatorFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver, TokenService tokenService) {
        this.resolver = resolver;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        try {
            if (request.getRequestURI().equals("/signin") || request.getRequestURI().equals("/signup") || requestURI.startsWith("/signup/confirm/")
            || request.getRequestURI().equals("/signup/check") || request.getRequestURI().equals("/nikita")) {
                filterChain.doFilter(request, response);
            } else {
                String token = getJwtTokenFromRequest(request);
                if (token != null) {
                    String username = tokenService.validateToken(token);
                    tokenService.setAuthentication(username);
                    tokenService.getTokenInfo(token);
                    filterChain.doFilter(request, response);
                } else
                    throw new InvalidTokenException("Login first");
            }
        } catch (Exception e) {
            resolver.resolveException(request, response, null, e);
        }
    }

    String getJwtTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7, bearerToken.length());
        else
            return null;
    }
}
