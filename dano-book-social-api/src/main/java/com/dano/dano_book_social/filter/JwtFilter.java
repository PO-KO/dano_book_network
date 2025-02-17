package com.dano.dano_book_social.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dano.dano_book_social.entity.UserEntity;
import com.dano.dano_book_social.repos.UserRepo;
import com.dano.dano_book_social.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepo userRepo;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        
                String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {

                    String jwt = authHeader.substring(7);
                    String email = jwtService.extractEmail(jwt);
                    if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Invalid token"));

                        if(jwtService.validateToken(jwt, user)) {

                            var token = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(token);
                        }
                    }
                            
                        

                }

                filterChain.doFilter(request, response);

    }
    
}
