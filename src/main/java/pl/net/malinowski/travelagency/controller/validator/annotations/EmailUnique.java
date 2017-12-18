package pl.net.malinowski.travelagency.controller.validator.annotations;

import pl.net.malinowski.travelagency.controller.validator.classes.EmailUniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = EmailUniqueValidator.class)
public @interface EmailUnique {
    String message() default "Ten email jest zarejestrowany!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
