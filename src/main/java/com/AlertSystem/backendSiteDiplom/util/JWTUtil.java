package com.AlertSystem.backendSiteDiplom.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;
    public String generateToken(String login, String password){
        Date exprationDate =
                Date.from(ZonedDateTime.now().plusMinutes(60 * 24).toInstant());
        return JWT.create()
                .withSubject("User Details")
                .withClaim("login", login)
                .withClaim("password", password)
                .withIssuedAt(new Date())
                .withIssuer("AlertSystemSite")
                .withExpiresAt(exprationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateToken(String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
                                            .withSubject("User Details")
                                            .withIssuer("AlertSystemSite")
                                            .build();

        DecodedJWT jwt = jwtVerifier.verify(token);
        return jwt.getClaim("login").asString();
    }
}
