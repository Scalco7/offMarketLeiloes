package com.backend.offMarketLeiloes.web.advice;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
@Schema(description = "Objeto de erro de validação (herda de StandardError)")
public class ValidationError extends StandardError {
    @Schema(description = "Lista de erros por campo")
    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Integer status, String error, String message, String path) {
        super(status, error, message, path);
    }

    public void addError(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }

    @Getter
    @Schema(description = "Informação do erro em um campo específico")
    public static class FieldMessage {
        @Schema(description = "Nome do campo com erro", example = "email")
        private String field;
        @Schema(description = "Mensagem de erro do campo", example = "E-mail inválido")
        private String message;

        public FieldMessage(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}
