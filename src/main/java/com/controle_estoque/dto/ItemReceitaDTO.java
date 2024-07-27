package com.controle_estoque.dto;

import com.controle_estoque.model.Grandeza;
import lombok.Data;

@Data
public class ItemReceitaDTO {

    private Long idItemEstoque;

    private Double quantidade;

    private Grandeza grandeza;

}
