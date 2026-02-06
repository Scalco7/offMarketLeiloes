package com.backend.offMarketLeiloes.application.features.account.commands.createAccount;

import org.springframework.stereotype.Service;

import com.backend.offMarketLeiloes.application.features.account.commands.createAccount.viewModels.CreateAccountRequest;
import com.backend.offMarketLeiloes.application.features.authentication.AuthenticationResponse;
import com.backend.offMarketLeiloes.domain.entities.Account;
import com.backend.offMarketLeiloes.domain.repositories.AccountRepository;
import com.backend.offMarketLeiloes.infrastructure.authentication.PasswordHashService;
import com.backend.offMarketLeiloes.infrastructure.authentication.TokenService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateAccountCommand {

    private final AccountRepository userRepository;
    private final TokenService tokenService;
    private final PasswordHashService passwordHashService;

    @Transactional
    public AuthenticationResponse execute(CreateAccountRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Usuário já cadastrado com este email");
        }

        Account user = new Account();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordHashService.gerarHashSenha(request.getPassword()));

        userRepository.save(user);

        return tokenService.generateAuthenticationResponse(user);
    }
}
