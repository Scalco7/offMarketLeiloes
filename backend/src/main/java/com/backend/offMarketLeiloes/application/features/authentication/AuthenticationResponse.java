package com.backend.offMarketLeiloes.application.features.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Resposta contendo os tokens de autenticação")
public class AuthenticationResponse {
    @Schema(description = "Token de acesso (Bearer Token) com curta duração", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(description = "Token de renovação com longa duração", example = "8f4e2d31-ce83-4903-a12c-da0f4439c288")
    private String refreshToken;
}
