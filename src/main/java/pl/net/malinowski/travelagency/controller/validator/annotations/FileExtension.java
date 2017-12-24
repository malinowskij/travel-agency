package pl.net.malinowski.travelagency.controller.validator.annotations;

import pl.net.malinowski.travelagency.controller.validator.classes.EmailUniqueValidator;
import pl.net.malinowski.travelagency.controller.validator.classes.FileExtensionValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = FileExtensionValidator.class)
public @interface FileExtension {
    String message() default "Zły format pliku!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String[] elements();
}
