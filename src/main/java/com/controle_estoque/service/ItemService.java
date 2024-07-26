package com.controle_estoque.service;

import com.controle_estoque.model.Item;
import com.controle_estoque.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public Item findById(Long id) {
        return itemRepository.getById(id);
    }

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public void delete(Item id) {
        itemRepository.delete(id);
    }
}
