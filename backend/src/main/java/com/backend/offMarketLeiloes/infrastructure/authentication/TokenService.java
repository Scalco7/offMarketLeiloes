package com.backend.offMarketLeiloes.infrastructure.authentication;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.backend.offMarketLeiloes.application.features.authentication.AuthenticationResponse;
import com.backend.offMarketLeiloes.domain.entities.RefreshToken;
import com.backend.offMarketLeiloes.domain.entities.Account;
import com.backend.offMarketLeiloes.domain.repositories.RefreshTokenRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private final RefreshTokenRepository refreshTokenRepository;

    public String generateToken(Account account) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("offMarketLeiloes")
                    .withSubject(account.getEmail())
                    .withClaim("accountId", account.getId().toString())
                    .withExpiresAt(genExpirationDate(2)) // 2 hours
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("offMarketLeiloes")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    @Transactional
    public String createRefreshToken(Account account) {
        String token = UUID.randomUUID().toString();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setAccount(account);
        refreshToken.setExpiresAt(
                LocalDateTime.ofInstant(genRefreshTokenExpirationDate(), ZoneOffset.of("-03:00")));

        refreshTokenRepository.save(refreshToken);

        return token;
    }

    @Transactional
    public AuthenticationResponse generateAuthenticationResponse(Account account) {
        String accessToken = generateToken(account);
        String refreshToken = createRefreshToken(account);

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    private Instant genExpirationDate(int hours) {
        return LocalDateTime.now().plusHours(hours).toInstant(ZoneOffset.of("-03:00"));
    }

    private Instant genRefreshTokenExpirationDate() {
        return LocalDateTime.now().plusDays(30).toInstant(ZoneOffset.of("-03:00"));
    }
}
