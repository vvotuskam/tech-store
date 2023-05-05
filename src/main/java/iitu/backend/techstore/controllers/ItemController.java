package iitu.backend.techstore.controllers;

import iitu.backend.techstore.exceptions.EntityException;
import iitu.backend.techstore.exceptions.EntityNotFoundException;
import iitu.backend.techstore.models.Item;
import iitu.backend.techstore.models.User;
import iitu.backend.techstore.services.ItemService;
import iitu.backend.techstore.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final UserService userService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("items", itemService.getAll());
        return "items/list-all";
    }

    @GetMapping("/{id}")
    public String getItem(Model model, @PathVariable int id) {
        Optional<Item> optional = itemService.getById(id);
        if (optional.isEmpty()) {
            return "redirect:/404";
        }
        model.addAttribute("item", optional.get());
        return "items/item-info";
    }

    @GetMapping("/new")
    public String getNewPage(@ModelAttribute("item") Item item) {
        return "items/item-save";
    }

    @PostMapping("/new")
    public String saveItem(@ModelAttribute("item") Item item) {
        itemService.save(item);
        return "redirect:/items";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Optional<Item> optional = itemService.getById(id);
        if (optional.isEmpty()) {
            return "redirect:/404";
        }
        model.addAttribute("item", optional.get());
        return "items/item-edit";
    }

    @PatchMapping("/{id}/edit")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("item") @Valid Item item,
                         BindingResult result) {
        if (result.hasErrors()) return "";
        itemService.update(id, item);
        return "redirect:/items";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable int id) {
        System.out.println("reached deletion");
        itemService.delete(id);
        return "redirect:/items";
    }

    @PatchMapping("/{id}/basket")
    public String addToBasket(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = (User) userService.loadUserByUsername(currentPrincipalName);

        Optional<Item> optional = itemService.getById(id);
        if (optional.isEmpty())
            return "redirect:/404";

        userService.addToBasket(user.getId(), optional.get());

        return "redirect:/items";
    }

    @PatchMapping("/{id}/basket-remove")
    public String addToBasketRemove(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = (User) userService.loadUserByUsername(currentPrincipalName);

        Optional<Item> optional = itemService.getById(id);
        if (optional.isEmpty())
            return "redirect:/404";

        userService.removeFromBasket(user.getId(), optional.get());

        return "redirect:/items";
    }
}
