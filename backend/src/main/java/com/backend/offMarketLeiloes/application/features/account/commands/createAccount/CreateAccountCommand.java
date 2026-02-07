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

    private final AccountRepository accountRepository;
    private final TokenService tokenService;
    private final PasswordHashService passwordHashService;

    @Transactional
    public AuthenticationResponse execute(CreateAccountRequest request) {
        if (accountRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Conta já cadastrada com este email");
        }

        Account account = new Account();
        account.setName(request.getName());
        account.setEmail(request.getEmail());
        account.setPassword(passwordHashService.generateHashPassword(request.getPassword()));

        accountRepository.save(account);

        return tokenService.generateAuthenticationResponse(account);
    }
}
