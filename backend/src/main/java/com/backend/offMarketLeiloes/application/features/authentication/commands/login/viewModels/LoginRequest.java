package com.backend.offMarketLeiloes.application.features.authentication.commands.login.viewModels;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para realização de login")
public class LoginRequest {
    @Schema(description = "Endereço de e-mail do usuário", example = "usuario@exemplo.com")
    private String email;

    @Schema(description = "Senha de acesso do usuário", example = "minhasenha123")
    private String password;
}
