package com.backend.offMarketLeiloes.application.features.account.commands.createAccount.viewModels;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para criação de um novo usuário")
public class CreateAccountRequest {
    @NotBlank(message = "O nome é obrigatório")
    @Schema(description = "Nome completo do usuário", example = "João da Silva")
    private String name;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    @Schema(description = "Endereço de e-mail único do usuário", example = "joao.silva@exemplo.com")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Schema(description = "Senha de acesso (mínimo 6 caracteres)", example = "senha123")
    private String password;
}
