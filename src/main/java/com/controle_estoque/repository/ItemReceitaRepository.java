package com.controle_estoque.repository;

import com.controle_estoque.model.ItemReceita;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemReceitaRepository extends JpaRepository<ItemReceita, Long> {

    List<ItemReceita> findByReceitaId(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM ItemReceita ir WHERE ir.receita.id = :receitaId")
    void deleteByReceitaId(@Param("receitaId") Long receitaId);
}
