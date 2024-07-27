package com.controle_estoque.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReceitaDTO {

    private String descricao;

    private List<ItemReceitaDTO> itens;

}
