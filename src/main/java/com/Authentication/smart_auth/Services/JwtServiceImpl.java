package com.Authentication.smart_auth.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

// The claims object is essentially a hashmap with various keys like, subject, expiration etc
// along with any custom key that you may have put in.
@Service
public class JwtServiceImpl implements JwtService {

    private String key=null;

    @Override
    public String generateToken(String username, List<String> role_name) {
        HashMap<String,Object> claims=new HashMap<>();
        claims.put("roles",role_name);   // adding custom key to claims map
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuer("ARCHIVIST")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+(10*60*1000)))
                .and()
                .signWith(generateKey())
                .compact();
    }

    @Override
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {
        Claims claims=getClaims(token);
        return  claims;
    }

    @Override
    public List<String> extractRole(String token) {
        List<Object> roles_obj=extractClaims(token).get("roles",List.class);
        List<String> roles=new ArrayList<>();
        for(Object obj : roles_obj) {
            if(obj instanceof String)
                roles.add((String)obj);
        }
        return roles;
    }

    private Claims getClaims(String token) {
        Claims claims=Jwts
                .parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims;
    }

    @Override
    public boolean isValidToken(String token, UserDetails user_details) {
        if(user_details==null)
            return false;
        return user_details.getUsername().equals(extractUsername(token)) && isValidDate(token);
    }

    private boolean isValidDate(String token) {
        return extractExpiration(token).after(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    private SecretKey generateKey() {
        byte[] decode=Decoders.BASE64.decode(getKey());
        return Keys.hmacShaKeyFor(decode);
    }

    private String getKey() {
        key="7uyfriImbYeXUQe8mvLs0tjSfpUpX8AgLzkIXMguyhs=";
        return key;
    }
}
