package com.example.utils;

import com.example.entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.jsonwebtoken.Jwts.*;

@Component
public class JwtUtils {

    //TODO: return Field injection from Properties!!
    //@Value("${jwt.secret}")
    private String secretKey = "secretKey";

   // @Value("${jwt.lifetime}")
    private String jwtLifetimeString = "PT30M";

    private Duration jwtLifetime;

    @PostConstruct
    public void init() {
        this.jwtLifetime = Duration.parse(jwtLifetimeString);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    List<Role> getRoles(String token) {
        return getAllClaimsFromToken(token).get("roles", List.class);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
        claims.put("roles", authorities);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtLifetime.toMillis());
        SecretKey key = getSecretKey();
        return builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }
}
