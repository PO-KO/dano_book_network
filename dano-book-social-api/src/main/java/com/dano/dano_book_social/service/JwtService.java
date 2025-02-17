package com.dano.dano_book_social.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final long jwtExp;
    private final String sk;

    public JwtService(
        @Value("${application.security.jwt.exp}")
        long jwtExp, 
        @Value("${application.security.jwt.secret-key}") 
        String sk) {
            
        this.jwtExp = jwtExp;
        this.sk = sk;
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(sk));
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    private String generateToken(Map<String,Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails, jwtExp);
    }

    private String buildToken(Map<String, Object> claims, UserDetails userDetails, long jwtExp) {
        // var authorities = userDetails
        //         .getAuthorities()
        //         .stream()
        //         .map(GrantedAuthority::getAuthority)
        //         .collect(Collectors.toList());

        return Jwts.builder()
                .claims()
                .add(claims)
                .add("authorities", userDetails.getAuthorities())
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExp))
                .and()
                .signWith(getKey())
                .compact();
    }

    public String extractEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T>claimResolver){
        final Claims claims = extractAllClaims(token);

        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
            return Jwts
                    .parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();


    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
