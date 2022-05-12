package io.github.willianlds.security.jwt;

import io.github.willianlds.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiration}")
    private String expiration;
    @Value("${security.jwt.key-signature}")
    private String keySignature;

    public String generatedToken(User user){
        long expirationString = Long.valueOf(expiration);
        LocalDateTime dateHourExpiration = LocalDateTime.now().plusMinutes(expirationString);
        Instant instant = dateHourExpiration.atZone(ZoneId.systemDefault()).toInstant();
        Date dateExpiration = Date.from(instant);

        return Jwts
                .builder()
                .setSubject(user.getUsername())
                .setExpiration(dateExpiration)
                .signWith(SignatureAlgorithm.HS512, keySignature)
                .compact();
    }

    private Claims getClaims( String token ) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(keySignature)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValid (String token){
        try {
            Claims claims = getClaims(token);
            Date dateExpiration = claims.getExpiration();
            LocalDateTime localDateTimeToken = dateExpiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            return !LocalDateTime.now().isAfter(localDateTimeToken);
        }catch (Exception e){
            return false;
        }
    }

    public String getLoginUser(String token) throws ExpiredJwtException{
        return (String) getClaims(token).getSubject();
    }
}
