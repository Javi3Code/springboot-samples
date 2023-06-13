package org.jeycode.samples.api_rest.aaa_core.exceptions_handler.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jeycode.samples.api_rest.aaa_core.exceptions_handler.validators.SearchFieldsValidator;
import org.jeycode.samples.domain.aaa_core.search.dto.ObjectType;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SearchFieldsValidator.class)
public @interface SearchFields {

  String message() default "The fields %s are not valid fields to return";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  @NotNull
  ObjectType of();

  String auxMessage() default "The fields %s are not valid fields to filter";
}
