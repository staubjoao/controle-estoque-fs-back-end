package com.controle_estoque.dto;

import com.controle_estoque.model.ItemReceita;
import com.controle_estoque.model.Receita;
import lombok.Data;

import java.util.List;

@Data
public class ReceitaRetornoDTO {

    private Receita receita;

    private List<ItemReceitaDTO> itens;

}
