package com.controle_estoque.dto;

import com.controle_estoque.model.Grandeza;
import lombok.Data;

@Data
public class ItemEstoqueDTO {

    private String descricao;

    private Double quantidade;

    private Grandeza grandeza;

    private Long localEstoqueId;

}
