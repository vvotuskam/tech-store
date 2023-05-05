package iitu.backend.techstore.controllers;

import iitu.backend.techstore.models.Item;
import iitu.backend.techstore.models.User;
import iitu.backend.techstore.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users/list-all";
    }

    @GetMapping("/{id}")
    public String getItem(Model model, @PathVariable int id) {
        Optional<User> optional = userService.getById(id);
        if (optional.isEmpty()) {
            return "redirect:/404";
        }
        model.addAttribute("user", optional.get());
        return "users/item-info";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Optional<User> optional = userService.getById(id);
        if (optional.isEmpty()) {
            return "redirect:/404";
        }
        model.addAttribute("user", optional.get());
        return "users/item-edit";
    }

    @PatchMapping("/{id}/edit")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("user") @Valid User item,
                         BindingResult result) {
        System.out.println("update reached");
        //if (result.hasErrors()) return "redirect:/users/" + id + "/edit";
        userService.update(id, item);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable int id) {
        userService.delete(id);
        return "redirect:/users";
    }
}
