package com.controle_estoque.service;

import com.controle_estoque.dto.AtualizarQuantidadeEstoqueDTO;
import com.controle_estoque.dto.ItemEstoqueDTO;
import com.controle_estoque.model.Grandeza;
import com.controle_estoque.model.ItemEstoque;
import com.controle_estoque.model.LocalEstoque;
import com.controle_estoque.repository.ItemEstoqueRepository;
import com.controle_estoque.repository.LocalEstoqueRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controle_estoque.model.Grandeza.*;

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
        itemEstoque.setQuantidadeMinima(itemEstoqueDTO.getQuantidadeMinima());
        itemEstoque.setGrandeza(itemEstoqueDTO.getGrandeza());
        itemEstoque.setLocalEstoque(localEstoque);

        return itemEstoqueRepository.save(itemEstoque);
    }

    public ItemEstoque atualizarQuantidadeEstoque(AtualizarQuantidadeEstoqueDTO atualizarQuantidadeEstoqueDTO) {
        ItemEstoque itemEstoque = itemEstoqueRepository.findById(atualizarQuantidadeEstoqueDTO.getIdItemEstoque())
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        if (!saoGrandazasCompativeis(atualizarQuantidadeEstoqueDTO.getGrandeza(), itemEstoque.getGrandeza())) {
            return null;
        }

        if (!itemEstoque.getGrandeza().equals(atualizarQuantidadeEstoqueDTO.getGrandeza())) {
            atualizarQuantidadeEstoqueDTO.setQuantidade(converterQuantidade(atualizarQuantidadeEstoqueDTO.getQuantidade(),
                    atualizarQuantidadeEstoqueDTO.getGrandeza(), itemEstoque.getGrandeza()));
        }

        itemEstoque.setQuantidade(itemEstoque.getQuantidade() + atualizarQuantidadeEstoqueDTO.getQuantidade());

        return itemEstoqueRepository.save(itemEstoque);
    }

    private boolean saoGrandazasCompativeis(Grandeza grandezaDTO, Grandeza grandezaItem) {
        if ((isPeso(grandezaDTO) && isPeso(grandezaItem)) ||
                (isVolume(grandezaDTO) && isVolume(grandezaItem))) {
            return true;
        }
        return false;
    }

    private boolean isPeso(Grandeza grandeza) {
        return grandeza == Grandeza.gramas || grandeza == Grandeza.kilos;
    }

    private boolean isVolume(Grandeza grandeza) {
        return grandeza == Grandeza.mililitros || grandeza == Grandeza.litros;
    }

    private boolean isGrama(Grandeza grandeza) {
        return grandeza == Grandeza.gramas;
    }

    private boolean isKilos(Grandeza grandeza) {
        return grandeza == Grandeza.kilos;
    }

    private boolean isMililitros(Grandeza grandeza) {
        return grandeza == Grandeza.mililitros;
    }

    private boolean isLitros(Grandeza grandeza) {
        return grandeza == Grandeza.litros;
    }

    private double converterQuantidade(double quantidade, Grandeza grandezaOrigem, Grandeza grandezaDestino) {
        if ((isGrama(grandezaOrigem) && isGrama(grandezaDestino))
                || (isKilos(grandezaOrigem) && isKilos(grandezaDestino))
                || (isMililitros(grandezaOrigem) && isMililitros(grandezaDestino))
                || (isLitros(grandezaOrigem) && isLitros(grandezaDestino))) {
            return quantidade;
        }

        if ((isKilos(grandezaOrigem) && isGrama(grandezaDestino))
                || (isLitros(grandezaOrigem) && isMililitros(grandezaDestino))) {
            return quantidade * 1000;
        }

        if ((isGrama(grandezaOrigem) && isKilos(grandezaDestino))
                || (isMililitros(grandezaOrigem) && isLitros(grandezaDestino))) {
            return quantidade / 1000;
        }

        throw new IllegalArgumentException("Conversão de unidades não suportada");
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

    public ItemEstoque alterar(ItemEstoque itemEstoque) {
        return itemEstoqueRepository.save(itemEstoque);
    }
}
