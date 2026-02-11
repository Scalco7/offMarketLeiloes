package com.backend.offMarketLeiloes.application.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValueValidator.class)
@Documented
public @interface EnumValue {
    Class<? extends Enum<?>> enumClass();

    String message() default "Valor inválido para o campo. Valores aceitos: {values}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
