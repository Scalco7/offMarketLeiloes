package com.backend.offMarketLeiloes.application.features.authentication.commands.login;

import org.springframework.stereotype.Service;

import com.backend.offMarketLeiloes.application.features.authentication.AuthenticationResponse;
import com.backend.offMarketLeiloes.application.features.authentication.commands.login.viewModels.LoginRequest;
import com.backend.offMarketLeiloes.domain.entities.Account;
import com.backend.offMarketLeiloes.domain.repositories.AccountRepository;
import com.backend.offMarketLeiloes.infrastructure.authentication.PasswordHashService;
import com.backend.offMarketLeiloes.infrastructure.authentication.TokenService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginCommand {

    private final AccountRepository userRepository;
    private final TokenService tokenService;
    private final PasswordHashService passwordHashService;

    @Transactional
    public AuthenticationResponse execute(LoginRequest request) {
        Account user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordHashService.verificarSenha(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        return tokenService.generateAuthenticationResponse(user);
    }
}
