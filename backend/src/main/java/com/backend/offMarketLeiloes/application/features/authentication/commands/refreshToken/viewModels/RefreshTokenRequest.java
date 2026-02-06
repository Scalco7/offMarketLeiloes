package com.backend.offMarketLeiloes.application.features.authentication.commands.refreshToken.viewModels;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para renovação de token")
public class RefreshTokenRequest {
    @Schema(description = "Refresh token válido recebido no login", example = "8f4e2d31-ce83-4903-a12c-da0f4439c288")
    private String refreshToken;

    @Schema(description = "ID único do usuário", example = "550e8400-e29b-41d4-a716-446655440000")
    private String userId;
}
