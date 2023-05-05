package iitu.backend.techstore.services.impl;

import iitu.backend.techstore.models.Item;
import iitu.backend.techstore.models.User;
import iitu.backend.techstore.repositories.UserRepository;
import iitu.backend.techstore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(int id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public void update(int id, User updated) {
        User user = getById(id).get();

        updated.setPassword(user.getPassword());
        updated.setId(id);
        updated.setRole(user.getRole());

        userRepository.save(updated);
    }



    @Override
    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addToBasket(int id, Item item) {
        User user = getById(id).get();
        user.getItems().add(item);
        item.getUsers().add(user);
    }

    @Override
    @Transactional
    public void removeFromBasket(int id, Item item) {
        User user = getById(id).get();
        user.getItems().remove(item);
        item.getUsers().remove(user);
    }
}
