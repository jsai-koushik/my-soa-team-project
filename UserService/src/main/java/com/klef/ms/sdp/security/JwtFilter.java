package com.klef.ms.sdp.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.klef.ms.sdp.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter 
{
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService service;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException 
    {
        String path = request.getServletPath();

        boolean isPublicEndpoint =
                path.equals("/user/")
                || path.equals("/user/login")
                || path.equals("/user/add")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/actuator");

        if (isPublicEndpoint) 
        {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        System.out.println("==================================");
        System.out.println("Request Path = " + path);
        System.out.println("Authorization Header = " + header);

        if (header == null || !header.startsWith("Bearer ")) 
        {
            sendErrorResponse(
                    response,
                    401,
                    "Authorization header is missing or invalid"
            );
            return;
        }

        String token = header.substring(7).trim();

        try 
        {
            String username = jwtUtil.extractUsername(token);

            System.out.println("Username from JWT = " + username);

            if (SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null) 
            {
                UserDetails userDetails =
                        service.loadUserByUsername(username);

                System.out.println(
                        "Username from UserDetails = "
                        + userDetails.getUsername()
                );

                System.out.println(
                        "Authorities = "
                        + userDetails.getAuthorities()
                );

                if (userDetails != null
                        && jwtUtil.validateToken(
                                token,
                                userDetails)) 
                {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authToken);

                    System.out.println(
                            "JWT validation successful"
                    );

                    System.out.println(
                            "Authentication = "
                            + SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                    );

                    System.out.println(
                            "Granted Authorities = "
                            + SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getAuthorities()
                    );
                } 
                else 
                {
                    System.out.println(
                            "JWT validation FAILED"
                    );

                    sendErrorResponse(
                            response,
                            401,
                            "Invalid or expired token"
                    );
                    return;
                }
            }
        } 
        catch (Exception e) 
        {
            System.out.println(
                    "JWT ERROR = " + e.getMessage()
            );

            e.printStackTrace();

            sendErrorResponse(
                    response,
                    401,
                    "Invalid token"
            );
            return;
        }

        chain.doFilter(request, response);
    }

    private void sendErrorResponse(
            HttpServletResponse response,
            int status,
            String message)
            throws IOException 
    {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse =
                "{\"error\":\"Unauthorized\",\"message\":\""
                + message
                + "\"}";

        response.getWriter().write(jsonResponse);
    }
}