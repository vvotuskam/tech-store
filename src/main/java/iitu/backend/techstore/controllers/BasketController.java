package iitu.backend.techstore.controllers;

import iitu.backend.techstore.models.User;
import iitu.backend.techstore.services.ItemService;
import iitu.backend.techstore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/basket")
public class BasketController {

    private final ItemService itemService;
    private final UserService userService;

    @GetMapping
    public String getBasket(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = (User) userService.loadUserByUsername(currentPrincipalName);

        model.addAttribute("items", user.getItems());

        return "basket/basket";
    }
}
