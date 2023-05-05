package iitu.backend.techstore.services.impl;

import iitu.backend.techstore.models.Item;
import iitu.backend.techstore.repositories.ItemRepository;
import iitu.backend.techstore.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultItemService implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public void save(Item item) {
        itemRepository.save(item);
    }

    @Override
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    @Override
    public Optional<Item> getById(int id) {
        return itemRepository.findById(id);
    }

    @Override
    @Transactional
    public void update(int id, Item updated) {
        updated.setId(id);
        itemRepository.save(updated);
    }

    @Override
    @Transactional
    public void delete(int id) {
        itemRepository.deleteById(id);
    }
}
