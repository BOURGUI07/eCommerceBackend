/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.api.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import main.exception.JWTException;
import main.repo.LocalUserRepo;
import main.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author hp
 */
@Component
public class JWTRequestFilter extends OncePerRequestFilter{
    @Autowired
    public JWTRequestFilter(JWTService service,LocalUserRepo repo) {
        this.service = service;
        this.repo=repo;
    }
    private final JWTService service;
    private final LocalUserRepo repo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            var token = tokenHeader.substring(7);
            try {
                var username = service.getUsernameFromToken(token);
                var opUser = repo.findByUsernameIgnoreCase(username);
                if(opUser.isPresent()){
                    var user = opUser.get();
                    var authentication  = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired JWT token");
                return;
            } catch (JWTException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("JWT processing error: " + e.getMessage());
                return;
            }
        }
        filterChain.doFilter(request, response);
        
    }
    
}
