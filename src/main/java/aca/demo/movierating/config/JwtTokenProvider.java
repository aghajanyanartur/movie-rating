package aca.demo.movierating.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

    private String secretKey = "1asxa56sxasca6s8c1a8s1ca8s1cas81ca9s8c1a8sdcw98c9a8dc19w8e1c9we19ef1vf98g1b9yt81n98um1yujm1nr891bvc981ax91w9e8c1f9t81vby91tunj1u98tjn1rbve91rc89exq98we1cw89rveb1ryn91"; // Secret key for signing the token

    private long expirationMs = 3600000L;

    private final UserDetailsService myUserDetails;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", "USER");

        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
        System.out.println(userDetails.getAuthorities());
        return new JwtAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        DecodedJWT jwt = JWT.decode(token);
        var username = jwt.getClaim("username").asString();
        return username;
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {

        if (token == null || token.isEmpty()) {
            return false;
        }
        return true;
    }
}