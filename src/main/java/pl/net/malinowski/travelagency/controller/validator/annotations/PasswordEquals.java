package pl.net.malinowski.travelagency.controller.validator.annotations;

import pl.net.malinowski.travelagency.controller.validator.classes.PasswordEqualsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordEqualsValidator.class)
public @interface PasswordEquals {
    String message() default "Podane hasła nie są zgodne!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
