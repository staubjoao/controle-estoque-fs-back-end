package com.controle_estoque.service;

import com.controle_estoque.dto.ItemReceitaDTO;
import com.controle_estoque.dto.ReceitaDTO;
import com.controle_estoque.dto.ReceitaRetornoDTO;
import com.controle_estoque.model.ItemEstoque;
import com.controle_estoque.model.ItemReceita;
import com.controle_estoque.model.LocalEstoque;
import com.controle_estoque.model.Receita;
import com.controle_estoque.repository.ItemEstoqueRepository;
import com.controle_estoque.repository.ItemReceitaRepository;
import com.controle_estoque.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        return receitaSalva;
    }

    public Receita findById(Long id) {
        return receitaRepository.findById(id).get();
    }

    public void delete(Receita receita) {
        itemReceitaRepository.deleteByReceitaId(receita.getId());
        receitaRepository.delete(receita);
    }

    public ReceitaRetornoDTO altera(Long id, ReceitaDTO receitaDTO) {
        ReceitaRetornoDTO receitaRetornoDTO = new ReceitaRetornoDTO();
        Receita receita = new Receita();
        receita.setId(id);
        receita.setDescricao(receitaDTO.getDescricao());

        Receita receitaAlterada = receitaRepository.save(receita);

        itemReceitaRepository.deleteByReceitaId(receita.getId());

        List<ItemReceita> itensReceita = new ArrayList<>();

        for (ItemReceitaDTO item : receitaDTO.getItens()) {
            ItemEstoque itemEstoque = itemEstoqueRepository.findById(item.getIdItemEstoque()).get();
            ItemReceita itemReceita = new ItemReceita();
            itemReceita.setQuantidade(item.getQuantidade());
            itemReceita.setGrandeza(item.getGrandeza());
            itemReceita.setItemEstoque(itemEstoque);

            itemReceita.setReceita(receitaAlterada);

            ItemReceita itemReceitaRetorno = itemReceitaRepository.save(itemReceita);

            itensReceita.add(itemReceitaRetorno);
        }
        List<ItemReceitaDTO> itemReceitaDTOList = itensReceita.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        receitaRetornoDTO.setReceita(receitaAlterada);
        receitaRetornoDTO.setItens(itemReceitaDTOList);

        return receitaRetornoDTO;
    }

    public List<Receita> getAll() {
        return receitaRepository.findAll();
    }

    public ReceitaRetornoDTO listarReceita(Long idReceita) {
        List<ItemReceita> itemReceitas = itemReceitaRepository.findByReceitaId(idReceita);
        Receita receita = receitaRepository.findById(idReceita).orElseThrow(() -> new RuntimeException("Receita não encontrada"));

        List<ItemReceitaDTO> itemReceitaDTOList = itemReceitas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        ReceitaRetornoDTO receitaRetornoDTO = new ReceitaRetornoDTO();
        receitaRetornoDTO.setReceita(receita);
        receitaRetornoDTO.setItens(itemReceitaDTOList);

        return receitaRetornoDTO;
    }

    public List<ReceitaRetornoDTO> listarTodasReceitas() {
        List<Receita> receitas = receitaRepository.findAll();

        return receitas.stream()
                .map(this::convertToReceitaRetornoDTO)
                .collect(Collectors.toList());
    }

    private ReceitaRetornoDTO convertToReceitaRetornoDTO(Receita receita) {
        List<ItemReceita> itemReceitas = itemReceitaRepository.findByReceitaId(receita.getId());

        List<ItemReceitaDTO> itemReceitaDTOList = itemReceitas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        ReceitaRetornoDTO receitaRetornoDTO = new ReceitaRetornoDTO();
        receitaRetornoDTO.setReceita(receita);
        receitaRetornoDTO.setItens(itemReceitaDTOList);

        return receitaRetornoDTO;
    }

    private ItemReceitaDTO convertToDTO(ItemReceita itemReceita) {
        ItemReceitaDTO dto = new ItemReceitaDTO();
        dto.setIdItemEstoque(itemReceita.getItemEstoque().getId());
        dto.setQuantidade(itemReceita.getQuantidade());
        dto.setGrandeza(itemReceita.getGrandeza());
        return dto;
    }
}
