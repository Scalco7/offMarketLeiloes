package com.backend.offMarketLeiloes.infrastructure.authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordHashService {

    private final PasswordEncoder passwordEncoder;

    public String gerarHashSenha(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean verificarSenha(String password, String hash) {
        return passwordEncoder.matches(password, hash);
    }
}
