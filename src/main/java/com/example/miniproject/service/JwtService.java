package com.example.miniproject.service;

import com.example.miniproject.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECURITY_CODE = "503E635266556A586E3272357738782F413F4428472B4B6250645367566B5991";

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // generate token only from userdetails
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .claim("role", userDetails.getAuthorities())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000 * 200 * 60))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claim = extractAllClaims(token);
        return claimResolver.apply(claim);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECURITY_CODE);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        if (isTokenExpired(token) && userName.equals(userDetails.getUsername())) {
            throw new CustomException("Invalid token", HttpStatus.UNAUTHORIZED);
        }
        return true;
    }

    private boolean isTokenExpired(String token) {
        if (extractExpirationDate(token).before(new Date())) {
            throw new CustomException("Token is expired", HttpStatus.UNAUTHORIZED);
        }
        return false;
    }

    private Date extractExpirationDate(String token) {
        // TODO Auto-generated method stub
        return extractClaim(token, Claims::getExpiration);
    }
}
