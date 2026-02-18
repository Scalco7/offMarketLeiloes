package com.backend.offMarketLeiloes.application.features.authentication.commands.login;

import org.springframework.stereotype.Service;

import com.backend.offMarketLeiloes.application.features.authentication.AuthenticationResponse;
import com.backend.offMarketLeiloes.application.features.authentication.commands.login.viewModels.LoginRequest;
import com.backend.offMarketLeiloes.domain.entities.Account;
import com.backend.offMarketLeiloes.domain.repositories.AccountRepository;
import com.backend.offMarketLeiloes.infrastructure.authentication.PasswordHashService;
import com.backend.offMarketLeiloes.infrastructure.authentication.TokenService;

import com.backend.offMarketLeiloes.application.common.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginCommand {

    private final AccountRepository accountRepository;
    private final TokenService tokenService;
    private final PasswordHashService passwordHashService;

    @Transactional
    public AuthenticationResponse execute(LoginRequest request) {
        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("E-mail não encontrado.", HttpStatus.NOT_FOUND));

        if (!passwordHashService.verifyPassword(request.getPassword(), account.getPassword())) {
            throw new BusinessException("Senha inválida.", HttpStatus.UNAUTHORIZED);
        }

        return tokenService.generateAuthenticationResponse(account);
    }
}
