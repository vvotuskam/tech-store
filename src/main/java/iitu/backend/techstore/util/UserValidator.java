package iitu.backend.techstore.util;

import iitu.backend.techstore.models.User;
import iitu.backend.techstore.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        boolean exists = userRepository.findByEmail(user.getEmail()).isPresent();

        if (exists) {
            errors.rejectValue("email", "",
                    "User with email '" + user.getEmail() + "' is already exists");
        }

        String password = user.getPassword();
        if (password.length() < 8) {
            errors.rejectValue("password", "", "Password should be at least of size 8");
        }

        boolean matches = password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,50}$");
        if (!matches) {
            errors.rejectValue("password", "",
                    "Password should contain at least 1 lowercase, uppercase, number");
        }
    }
}
