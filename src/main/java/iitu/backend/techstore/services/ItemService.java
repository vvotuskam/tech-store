package iitu.backend.techstore.services;

import iitu.backend.techstore.models.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    void save(Item item);
    List<Item> getAll();
    Optional<Item> getById(int id);
    void update(int id, Item updated);
    void delete(int id);
}
