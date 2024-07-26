package com.controle_estoque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "item_estoque")
public class ItemEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String descricao;

    @Column(nullable = false)
    @NotNull
    private Double quantidade;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Grandeza grandeza;

    @ManyToOne
    private LocalEstoque localEstoque;

}
