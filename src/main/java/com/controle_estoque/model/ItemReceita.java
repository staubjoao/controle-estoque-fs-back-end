package com.controle_estoque.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "item_receita")
public class ItemReceita {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_estoque_id")
    private ItemEstoque itemEstoque;

    @Column
    private Double quantidade;

    @Column
    @Enumerated(EnumType.STRING)
    private Grandeza grandeza;

    @ManyToOne
    @JoinColumn(name = "receita_id")
    private Receita receita;
}
