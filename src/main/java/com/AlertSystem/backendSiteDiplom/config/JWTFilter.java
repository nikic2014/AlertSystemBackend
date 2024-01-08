package com.AlertSystem.backendSiteDiplom.config;

import com.AlertSystem.backendSiteDiplom.services.PeopleDetailsService;
import com.AlertSystem.backendSiteDiplom.util.CookieUtils;
import com.AlertSystem.backendSiteDiplom.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final PeopleDetailsService peopleDetailsService;

    @Autowired
    public JWTFilter(JWTUtil jwtUtil, PeopleDetailsService peopleDetailsService) {
        this.jwtUtil = jwtUtil;
        this.peopleDetailsService = peopleDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String originHeader = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", originHeader);
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        if (request.getRequestURI().equals("/auth/login") || request.getRequestURI().equals("/auth/register") || request.getRequestURI().equals("/bot/authProject")) {
            filterChain.doFilter(request, response);
            return;
        }

            String jwtToken = CookieUtils.getCookieValue(request, "jwt-token");

        try {
            String username = jwtUtil.validateToken(jwtToken);
            UserDetails userDetails =
                    peopleDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails,
                            userDetails.getPassword(),
                            userDetails.getAuthorities());

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid JWT token");
        }

        filterChain.doFilter(request, response);
    }
}
