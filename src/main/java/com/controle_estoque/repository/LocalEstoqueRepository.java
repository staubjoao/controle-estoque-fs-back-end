package com.controle_estoque.repository;

import com.controle_estoque.model.LocalEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalEstoqueRepository extends JpaRepository<LocalEstoque, Long> {
}
