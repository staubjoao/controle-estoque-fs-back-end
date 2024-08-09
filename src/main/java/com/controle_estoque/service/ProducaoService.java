package com.controle_estoque.service;

import com.controle_estoque.dto.ErroProducaoItemDTO;
import com.controle_estoque.dto.ItemReceitaDTO;
import com.controle_estoque.dto.ProducaoDTO;
import com.controle_estoque.dto.ReceitaRetornoDTO;
import com.controle_estoque.model.ItemEstoque;
import com.controle_estoque.model.Producao;
import com.controle_estoque.repository.ProducaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.controle_estoque.model.Grandeza.*;

@Service
public class ProducaoService {

    @Autowired
    private ProducaoRepository producaoRepository;

    @Autowired
    private ReceitaService receitaService;

    @Autowired
    private ItemEstoqueService itemEstoqueService;

    public ResponseEntity<?> adicionarProducao(ProducaoDTO producaoDTO) {
        Producao producao = new Producao();

        ReceitaRetornoDTO receitaCompleta = receitaService.listarReceita(producaoDTO.getReceitaId());

        producao.setReceita(receitaCompleta.getReceita());
        producao.setQuantidadeReceitas(producaoDTO.getQuantidadeReceitas());

        List<ErroProducaoItemDTO> errosProducao = new ArrayList<>();
        List<ItemEstoque> listaDeItensParaAtualizacao = new ArrayList<>();
        for (ItemReceitaDTO itemReceita : receitaCompleta.getItens()) {
            ItemEstoque itemEstoque = itemEstoqueService.findById(itemReceita.getIdItemEstoque());

            double quantidadeItemReceita = itemReceita.getQuantidade() * producaoDTO.getQuantidadeReceitas();
            double quantidadeEstoque = itemEstoque.getQuantidade();

            boolean converteuEstoque = false;
            if (!itemReceita.getGrandeza().equals(itemEstoque.getGrandeza())) {
                if (itemReceita.getGrandeza().equals(kilos) || itemReceita.getGrandeza().equals(litros)) {
                    quantidadeItemReceita = quantidadeItemReceita * 1000; // vira grama ou ml
                }
                if (itemEstoque.getGrandeza().equals(kilos) || itemEstoque.getGrandeza().equals(litros)) {
                    converteuEstoque = true;
                    quantidadeEstoque = quantidadeEstoque * 1000; // vira grama ou ml
                }
            }

            if (quantidadeItemReceita > quantidadeEstoque) {
                ErroProducaoItemDTO erroProducaoItemDTO = new ErroProducaoItemDTO();
                erroProducaoItemDTO.setItem(itemEstoque);
                erroProducaoItemDTO.setErro("Estoque insuficiente");

                errosProducao.add(erroProducaoItemDTO);
            } else {
                if(converteuEstoque) {
                    quantidadeItemReceita = quantidadeItemReceita / 1000;
                }
                itemEstoque.setQuantidade(itemEstoque.getQuantidade() - quantidadeItemReceita);
                listaDeItensParaAtualizacao.add(itemEstoque);
            }
        }

        if (!errosProducao.isEmpty()) {
            return ResponseEntity.badRequest().body(errosProducao);
        }

        for (ItemEstoque itemEstoque : listaDeItensParaAtualizacao) {
            itemEstoqueService.alterar(itemEstoque);
        }

        producaoRepository.save(producao);

        return ResponseEntity.ok("Produção cadastrada com sucesso!");
    }

    public List<Producao> getAll() {
        return producaoRepository.findAll();
    }
}
