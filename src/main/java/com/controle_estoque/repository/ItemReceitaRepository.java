package com.controle_estoque.repository;

import com.controle_estoque.model.ItemReceita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemReceitaRepository extends JpaRepository<ItemReceita, Long> {
}
