package com.controle_estoque.controller;

import com.controle_estoque.dto.ReceitaDTO;
import com.controle_estoque.model.Receita;
import com.controle_estoque.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/receita")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody ReceitaDTO receitaDTO) {
        try {
            Receita receita = receitaService.save(receitaDTO);
            return ResponseEntity.ok(receita);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao salvar a receita: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Receita> listar() {
        return receitaService.getAll();
    }

}
