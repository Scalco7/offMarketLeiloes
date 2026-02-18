package com.backend.offMarketLeiloes.web.advice;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ValidationError extends StandardError {
    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Integer status, String error, String message, String path) {
        super(status, error, message, path);
    }

    public void addError(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }

    @Getter
    public static class FieldMessage {
        private String field;
        private String message;

        public FieldMessage(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}
