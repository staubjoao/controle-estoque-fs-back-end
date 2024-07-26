package com.controle_estoque.service;

import com.controle_estoque.model.LocalEstoque;
import com.controle_estoque.repository.LocalEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalEstoqueService {

    @Autowired
    private LocalEstoqueRepository localEstoqueRepository;

    public LocalEstoque save(LocalEstoque item) {
        return localEstoqueRepository.save(item);
    }

    public LocalEstoque findById(Long id) {
        return localEstoqueRepository.getById(id);
    }

    public List<LocalEstoque> getAll() {
        return localEstoqueRepository.findAll();
    }

    public void delete(LocalEstoque id) {
        localEstoqueRepository.delete(id);
    }
}
