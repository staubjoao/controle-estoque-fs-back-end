package com.controle_estoque.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "producao")
public class Producao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receita_id")
    private Receita receita;

    @Column
    private Double quantidadeReceitas;

}
