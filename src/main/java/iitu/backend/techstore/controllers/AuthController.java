package iitu.backend.techstore.controllers;

import iitu.backend.techstore.models.Role;
import iitu.backend.techstore.models.User;
import iitu.backend.techstore.services.AuthService;
import iitu.backend.techstore.util.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    /*
    Admin: asulanma@gmail.com Password123
     */

    private final AuthService authService;
    private final UserValidator userValidator;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(@ModelAttribute("user") User user) {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid User user,
                           BindingResult result) {

        userValidator.validate(user, result);

        if (user.getRole() == null) {
            user.setRole(Role.ROLE_USER);
        }

        if (result.hasErrors()) {
            return "auth/register";
        }
        authService.register(user);
        System.out.println("REGISTERED");

        return "redirect:/auth/register";
    }
}
