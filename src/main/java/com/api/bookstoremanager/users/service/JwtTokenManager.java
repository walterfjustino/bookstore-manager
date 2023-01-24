package com.api.bookstoremanager.users.service;

import com.google.common.base.Function;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
//@AllArgsConstructor
public class JwtTokenManager {


//  @Value("${jwt.validity}")
  public Long jwtTokenValidty;

//  @Value("${jwt.secret}")
  private String secret;

  public JwtTokenManager(@Value("${jwt.validity}") Long jwtTokenValidty,
                         @Value("${jwt.secret}") String secret) {
    this.jwtTokenValidty = jwtTokenValidty;
    this.secret = secret;
  }

  public String generateToken(UserDetails userDetails){
    Map<String, Object> claims = new HashMap<>();
    return doGenerateToken(userDetails.getUsername(), claims);

  }

  private String doGenerateToken(String username, Map<String, Object> claims) {
    return Jwts.builder()
            .setClaims(claims).setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidty * 1000))
            .signWith(SignatureAlgorithm.HS512, secret).compact();
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
    Claims claims = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody();
    return claims;
  }

  public boolean validateToken (String token, UserDetails userDetails){
    var username = getUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

  }

  private boolean isTokenExpired(String token) {
    var expirationDate = getExpirationDateFromToken(token);
    return expirationDate.before(new Date());
  }

}
