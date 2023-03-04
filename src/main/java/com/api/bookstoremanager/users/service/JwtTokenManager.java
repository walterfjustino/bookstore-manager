package com.api.bookstoremanager.users.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTokenManager {

  @Value("${jwt.validity}")
  public Long jwtTokenValidty;

  @Value("${jwt.secret}")
  private String secret;

  public String generateToken(UserDetails userDetails){
    Map<String, Object> claims = new HashMap<>();
    return doGenerateToken(userDetails.getUsername(), claims);
  }

  private String doGenerateToken(String username, Map<String, Object> claims) {
    return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidty * 1000))
            .signWith(getSignInKey())
            .compact();
  }

  public String getUsernameFromToken(String token){
    return getClaimForToken(token, Claims::getSubject);
  }

  public Date getExpirationDateFromToken(String token){
    return getClaimForToken(token, Claims::getExpiration);
  }

  private <T> T getClaimForToken(String token,  Function<Claims, T> claimsResolver) {
    Claims claims = getAllClaimsForToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsForToken(String token) {
    return Jwts.parser()
             .setSigningKey(getSignInKey())
             .parseClaimsJws(token)
             .getBody();
  }

  public boolean validateToken (String token, UserDetails userDetails){
    var username = getUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

  }

  private boolean isTokenExpired(String token) {
    var expirationDate = getExpirationDateFromToken(token);
    return expirationDate.before(new Date());
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
