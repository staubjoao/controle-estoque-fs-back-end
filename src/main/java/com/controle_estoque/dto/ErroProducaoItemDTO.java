package com.controle_estoque.dto;

import com.controle_estoque.model.ItemEstoque;
import lombok.Data;

@Data
public class ErroProducaoItemDTO {

    private ItemEstoque item;

    private String erro;

}
