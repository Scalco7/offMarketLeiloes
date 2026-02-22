package com.backend.offMarketLeiloes.web.controllers;

import com.backend.offMarketLeiloes.web.advice.StandardError;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.offMarketLeiloes.application.features.authentication.AuthenticationResponse;
import com.backend.offMarketLeiloes.application.features.authentication.commands.login.LoginCommand;
import com.backend.offMarketLeiloes.application.features.authentication.commands.login.viewModels.LoginRequest;
import com.backend.offMarketLeiloes.application.features.authentication.commands.refreshToken.RefreshTokenCommand;
import com.backend.offMarketLeiloes.application.features.authentication.commands.refreshToken.viewModels.RefreshTokenRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para autenticação e renovação de tokens.")
public class AuthenticationController {

    private final LoginCommand loginCommand;
    private final RefreshTokenCommand refreshTokenCommand;

    @Operation(summary = "Realiza o login com email e senha", description = "Autentica a conta e retorna o par de tokens (Access Token e Refresh Token).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas (Email ou Senha incorretos)", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody @Valid LoginRequest request) {
        return loginCommand.execute(request);
    }

    @Operation(summary = "Renova o token de acesso", description = "Gera um novo Access Token e um novo Refresh Token utilizando um Refresh Token válido. Aplica rotação de Refresh Token por segurança.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tokens renovados com sucesso", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "401", description = "Refresh token inválido, expirado ou pertencente a outra conta", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping("/refresh")
    public AuthenticationResponse refresh(@RequestBody @Valid RefreshTokenRequest request) {
        return refreshTokenCommand.execute(request);
    }
}
