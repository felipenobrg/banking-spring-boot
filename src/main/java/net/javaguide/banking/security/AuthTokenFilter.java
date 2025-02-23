package net.javaguide.banking.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.javaguide.banking.security.jwt.JwtUtils;
import net.javaguide.banking.service.impl.UserDetailServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwtToken = parseJwt(request);
        System.out.println("[DEBUG] Extracted JWT: " + jwtToken);

        try {
            if (jwtToken != null && jwtUtils.validateJwtToken(jwtToken)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwtToken);
                System.out.println("[DEBUG] Authenticated User: " + username);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println("[DEBUG] Loaded User Details: " + userDetails.getUsername());
                System.out.println("[DEBUG] User Authorities: " + userDetails.getAuthorities());

                if (userDetails.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"))) {
                    System.out.println("[DEBUG] User has admin authority.");
                } else {
                    System.out.println("[DEBUG] User does not have admin authority.");
                }

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("[DEBUG] Authentication set for user: " + username);
            } else {
                System.out.println("[ERROR] JWT is invalid or null");
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Cannot set user authentication: " + e.getMessage());
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            System.out.println("[ERROR] No cookies found in request");
            return null;
        }

        for (Cookie cookie : cookies) {
            System.out.println("[DEBUG] Found Cookie: " + cookie.getName() + " = " + cookie.getValue());
            if ("jwt".equals(cookie.getName())) {
                System.out.println("[DEBUG] Extracted JWT from Cookie: " + cookie.getValue());
                return cookie.getValue();
            }
        }

        System.out.println("[ERROR] JWT Cookie Not Found!");
        return null;
    }
}
