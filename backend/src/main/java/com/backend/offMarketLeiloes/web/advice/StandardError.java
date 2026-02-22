package com.backend.offMarketLeiloes.web.advice;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Objeto de erro padrão da API")
public class StandardError {
    @Schema(description = "Timestamp do erro", example = "2024-02-22T12:00:00Z")
    private Instant timestamp;
    @Schema(description = "Código HTTP do erro", example = "400")
    private Integer status;
    @Schema(description = "Descrição curta do erro", example = "Erro de negócio")
    private String error;
    @Schema(description = "Mensagem detalhada do erro", example = "Recurso não encontrado")
    private String message;
    @Schema(description = "Caminho da rota que gerou o erro", example = "/api/resource")
    private String path;

    public StandardError(Integer status, String error, String message, String path) {
        this.timestamp = Instant.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
