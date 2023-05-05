package iitu.backend.techstore.services;

import iitu.backend.techstore.models.Item;
import iitu.backend.techstore.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    void save(User user);
    List<User> getAll();
    Optional<User> getById(int id);
    void update(int id, User updated);
    void delete(int id);

    @Transactional
    void addToBasket(int id, Item item);

    void removeFromBasket(int id, Item item);

}
