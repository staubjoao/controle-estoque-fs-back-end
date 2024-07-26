package com.controle_estoque.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "local_estoque")
public class LocalEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String descricao;

}
