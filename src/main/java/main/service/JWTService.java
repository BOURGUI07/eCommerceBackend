package main.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import javax.crypto.SecretKey;
import main.models.LocalUser;

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;
    private SignatureAlgorithm algorithm;
    private SecretKey secretKey;

    private static final String USERNAME_KEY = "USERNAME";
    private static final String EMAIL_KEY = "EMAIL";

    @PostConstruct
    public void postConstruct() {
        algorithm = SignatureAlgorithm.HS256; // Initialize the algorithm
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateJWT(LocalUser user) {
        long nowMillis = System.currentTimeMillis();
        long expiryMillis = nowMillis + expiryInSeconds * 1000;

        return Jwts.builder()
        .setIssuer(issuer)
        .claim(USERNAME_KEY, user.getUsername())
        .setIssuedAt(new Date(nowMillis))
        .setExpiration(new Date(expiryMillis))
        .signWith(secretKey, algorithm)
        .compact();
    }
    
    public String getUsernameFromToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .requireIssuer(issuer)
                .build()
                .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();
            return claims.get(USERNAME_KEY, String.class);
        } catch (SignatureException e) {
            // Handle invalid signature
            return null;
        } catch (Exception e) {
            // Handle other exceptions
            return null;
        }
    }
    
    public String generateVerificationJWT(LocalUser user){
        long nowMillis = System.currentTimeMillis();
        long expiryMillis = nowMillis + expiryInSeconds * 1000;

        return Jwts.builder()
        .setIssuer(issuer)
        .claim(EMAIL_KEY, user.getEmail())
        .setIssuedAt(new Date(nowMillis))
        .setExpiration(new Date(expiryMillis))
        .signWith(secretKey, algorithm)
        .compact();
    }
}


