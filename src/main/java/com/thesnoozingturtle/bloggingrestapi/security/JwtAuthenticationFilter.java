package com.thesnoozingturtle.bloggingrestapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesnoozingturtle.bloggingrestapi.payloads.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtTokenHelper jwtTokenHelper;

    private final ObjectMapper objectMapper;

    @Autowired
    public JwtAuthenticationFilter(UserDetailsService userDetailsService, JwtTokenHelper jwtTokenHelper, ObjectMapper objectMapper) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenHelper = jwtTokenHelper;
        this.objectMapper = objectMapper;
    }

    //it executes when api request are hit
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        //get the token
        String requestToken = request.getHeader("Authorization");
        String userName = null;
        String token = null;
        String message = "";
        if (requestToken != null && requestToken.startsWith("Bearer") && requestToken.length() >= 7) {
            token = requestToken.substring(7);
            try {
                userName = this.jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                message = "Unable to get JWT token";
                handleJwtExceptions(response, message);
                return;
            } catch (ExpiredJwtException e) {
                message = "JWT token has expired!";
                handleJwtExceptions(response, message);
                return;
            } catch (MalformedJwtException e) {
                message = "Invalid jwt";
                handleJwtExceptions(response, message);
                return;
            }
        }

        //now validate
        //SecurityContextHolder.getContext().getAuthentication() == null -> no one is authenticated right now
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            if (this.jwtTokenHelper.validateToken(token, userDetails)) {
                //do authentication
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void handleJwtExceptions(HttpServletResponse response, String message) throws IOException {
        ApiResponse apiResponse = new ApiResponse(message, false);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
