package main.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
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
}


