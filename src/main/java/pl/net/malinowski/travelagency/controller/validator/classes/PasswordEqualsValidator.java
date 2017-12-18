package pl.net.malinowski.travelagency.controller.validator.classes;

import pl.net.malinowski.travelagency.controller.validator.annotations.PasswordEquals;
import pl.net.malinowski.travelagency.data.entity.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordEqualsValidator implements ConstraintValidator<PasswordEquals, User> {
    @Override
    public void initialize(PasswordEquals passwordEquals) {

    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
