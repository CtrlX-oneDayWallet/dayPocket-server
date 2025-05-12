package hyu.dayPocket.utils;

import hyu.dayPocket.domain.Member;
import hyu.dayPocket.exceptions.JwtTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtils {
    private final Key key;

    private long expirationTime;

    public JwtTokenUtils(@Value("${jwt.secret-key}") String secretKey, @Value("${jwt.expire-time}") long expirationTime) {
        this.expirationTime = expirationTime;
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateAccessToken(Member member) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(member))
                .setIssuedAt(now)
                .setSubject(String.valueOf(member.getId()))
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Member member) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setHeader(createHeader())
                .setIssuedAt(now)
                .setSubject(String.valueOf(member.getId()))
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getRefreshToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw new JwtTokenException("Token is empty");
        }

        for (Cookie cookie : request.getCookies()) {
            if ("refreshToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        throw new JwtTokenException("Token is empty");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            throw new JwtTokenException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            throw new JwtTokenException("Expired");
        } catch (UnsupportedJwtException e) {
            throw new JwtTokenException("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            throw new JwtTokenException("Invalid JWT token");
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return header;
    }

    private Map<String, Object> createClaims(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("PhoneNumber", member.getPhoneNumber());
        return claims;
    }
}
