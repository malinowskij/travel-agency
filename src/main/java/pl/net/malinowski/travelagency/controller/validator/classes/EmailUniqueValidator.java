package pl.net.malinowski.travelagency.controller.validator.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import pl.net.malinowski.travelagency.controller.validator.annotations.EmailUnique;
import pl.net.malinowski.travelagency.data.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(EmailUnique emailUnique) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return userRepository.findByEmail(s) == null;
        } catch (NullPointerException ex) {
            return true;
        }
    }
}
