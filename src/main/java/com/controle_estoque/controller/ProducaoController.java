package com.controle_estoque.controller;

import com.controle_estoque.dto.ItemEstoqueDTO;
import com.controle_estoque.dto.ProducaoDTO;
import com.controle_estoque.model.ItemEstoque;
import com.controle_estoque.model.Producao;
import com.controle_estoque.service.ItemEstoqueService;
import com.controle_estoque.service.ProducaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producao")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProducaoController {

    @Autowired
    private ProducaoService producaoService;

    @GetMapping
    public List<Producao> listar() {
        return producaoService.getAll();
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody ProducaoDTO producaoDTO) {
        try {
            return producaoService.adicionarProducao(producaoDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao salvar o item: " + e.getMessage());
        }
    }
}
