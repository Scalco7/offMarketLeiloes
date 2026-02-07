package com.backend.offMarketLeiloes.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.offMarketLeiloes.application.features.account.commands.createAccount.CreateAccountCommand;
import com.backend.offMarketLeiloes.application.features.account.commands.createAccount.viewModels.CreateAccountRequest;
import com.backend.offMarketLeiloes.application.features.authentication.AuthenticationResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Tag(name = "Contas", description = "Rotas para gerenciamento de contas de acesso.")
public class AccountController {

    private final CreateAccountCommand createAccountCommand;

    @Operation(summary = "Cadastra uma nova conta", description = "Cria uma nova conta de acesso no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conta cadastrada com sucesso", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou email já cadastrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado ou token inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse create(@RequestBody @Valid CreateAccountRequest request) {
        return createAccountCommand.execute(request);
    }
}
