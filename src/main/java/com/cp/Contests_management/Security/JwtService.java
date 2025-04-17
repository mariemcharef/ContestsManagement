package com.cp.Contests_management.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//required
@Service
public class JwtService {

    private long jwtExpiration;

    private static final String secretKey="455F3C83F471CDDF68CEF81D85A1F455F3C83F471CDDF68CEF81D85A1F";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllClaim(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static Key getSignInKey() {
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims=extractAllClaim(token);
        return claimsResolver.apply(claims);
    }
    public String generateToken(UserDetails userDetails) {

        return generateToken(new HashMap<>(),userDetails);
    }
    public boolean isTokenValid(String token,UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }



    public String generateToken(Map<String, Object> extraClaims,
                                UserDetails userDetails) {
        extraClaims.put("email", userDetails.getUsername());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60*60*24))//minute a modifier
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailFromToken(String jwt) {
        final Logger logger = LoggerFactory.getLogger(JwtService.class);

        if (jwt == null || jwt.trim().isEmpty()) {
            logger.error("Empty or null JWT token provided");
            throw new IllegalArgumentException("Authorization token is required");
        }
        try {
            jwt = jwt.replaceFirst("^Bearer ", "");

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            if (!claims.containsKey("email")) {
                logger.error("JWT token missing required email claim");
            }
            String email = claims.get("email", String.class);
            if (email == null || email.trim().isEmpty()) {
                logger.error("Email claim is empty in JWT token");
                throw new IllegalArgumentException("Email claim cannot be empty");
            }

            return email;
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token: {}", ex.getMessage());
            throw new JwtException("Token has expired");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token: {}", ex.getMessage());
            throw new JwtException("Token format not supported");
        } catch (MalformedJwtException ex) {
            logger.error("Malformed JWT token: {}", ex.getMessage());
            throw new JwtException("Invalid token format");
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature: {}", ex.getMessage());
            throw new JwtException("Token signature validation failed");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty: {}", ex.getMessage());
            throw new JwtException("Invalid token claims");
        }
    }


}
