package com.controle_estoque.service;

import com.controle_estoque.dto.ItemReceitaDTO;
import com.controle_estoque.dto.ReceitaDTO;
import com.controle_estoque.model.ItemEstoque;
import com.controle_estoque.model.ItemReceita;
import com.controle_estoque.model.Receita;
import com.controle_estoque.repository.ItemEstoqueRepository;
import com.controle_estoque.repository.ItemReceitaRepository;
import com.controle_estoque.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private ItemReceitaRepository itemReceitaRepository;

    @Autowired
    private ItemEstoqueRepository itemEstoqueRepository;

    public Receita save(ReceitaDTO receitaDTO) {
        Receita receita = new Receita();
        receita.setDescricao(receitaDTO.getDescricao());

        Receita receitaSalva = receitaRepository.save(receita);

        for (ItemReceitaDTO item : receitaDTO.getItens()) {
            ItemEstoque itemEstoque = itemEstoqueRepository.findById(item.getIdItemEstoque()).get();
            ItemReceita itemReceita = new ItemReceita();
            itemReceita.setQuantidade(item.getQuantidade());
            itemReceita.setGrandeza(item.getGrandeza());
            itemReceita.setItemEstoque(itemEstoque);

            itemReceita.setReceita(receitaSalva);

            itemReceitaRepository.save(itemReceita);
        }
        return receitaRepository.save(receitaSalva);
    }


    public List<Receita> getAll() {
        return receitaRepository.findAll();
    }
}
