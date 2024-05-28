package com.example.TurfBookingApplication.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.sql.Date;
import java.util.*;

@Service
public class JwtService {

   final private String mySecretKey="0x4a836d2f981ec0b31a854adbb18efc03edabfde5fbc02c5823dfbd37a8104e4c";

    public String generateToken(String userName){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Base64.getDecoder().decode(mySecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (JwtException | IllegalArgumentException e) {
            // handle the exception here
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "JWT processing failed", e);
        }
    }

    public Authentication getAuthentication(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token);
        String username = claimsJws.getBody().getSubject();
        // If your application uses authorities, you should set them here.
        return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
    }


    public String getUsernameFromToken(String token) {
        String newToken=token.substring(7);
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(newToken).getBody();
            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            // handle the exception here
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "JWT processing failed", e);
        }
    }




}
