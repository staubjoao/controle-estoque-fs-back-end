package com.controle_estoque.service;

import com.controle_estoque.dto.ItemEstoqueDTO;
import com.controle_estoque.model.ItemEstoque;
import com.controle_estoque.model.LocalEstoque;
import com.controle_estoque.repository.ItemEstoqueRepository;
import com.controle_estoque.repository.LocalEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemEstoqueService {

    @Autowired
    private ItemEstoqueRepository itemEstoqueRepository;

    @Autowired
    private LocalEstoqueRepository localEstoqueRepository;

    public ItemEstoque save(ItemEstoqueDTO itemEstoqueDTO) {
        LocalEstoque localEstoque = null;
        ItemEstoque itemEstoque = new ItemEstoque();
        if (itemEstoqueDTO.getLocalEstoqueId() != 0) {
            localEstoque = localEstoqueRepository.findById(itemEstoqueDTO.getLocalEstoqueId()).get();
        }
        itemEstoque.setDescricao(itemEstoqueDTO.getDescricao());
        itemEstoque.setQuantidade(itemEstoqueDTO.getQuantidade());
        itemEstoque.setGrandeza(itemEstoqueDTO.getGrandeza());
        itemEstoque.setLocalEstoque(localEstoque);

        return itemEstoqueRepository.save(itemEstoque);
    }

    public ItemEstoque findById(Long id) {
        return itemEstoqueRepository.getById(id);
    }

    public List<ItemEstoque> getAll() {
        return itemEstoqueRepository.findAll();
    }

    public void delete(ItemEstoque id) {
        itemEstoqueRepository.delete(id);
    }
}
