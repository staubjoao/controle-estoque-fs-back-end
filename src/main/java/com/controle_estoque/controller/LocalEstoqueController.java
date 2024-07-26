package com.controle_estoque.controller;

import com.controle_estoque.model.LocalEstoque;
import com.controle_estoque.service.LocalEstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/local-estoque")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LocalEstoqueController {

    @Autowired
    private LocalEstoqueService localEstoqueService;

    @GetMapping
    public List<LocalEstoque> listar() {
        return localEstoqueService.getAll();
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody LocalEstoque localEstoque) {
        try {
            LocalEstoque localEstoqueSalvo = localEstoqueService.save(localEstoque);
            return ResponseEntity.ok(localEstoqueSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao salvar o local de estoque: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        try {
            LocalEstoque localEstoque = localEstoqueService.findById(id.longValue());
            if(localEstoque == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(localEstoque);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar o local de estoque: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            LocalEstoque localEstoque = localEstoqueService.findById(id.longValue());
            if(localEstoque == null) {
                return ResponseEntity.notFound().build();
            } else {
                localEstoqueService.delete(localEstoque);
                return ResponseEntity.ok(localEstoque);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deletar o local de estoque: " + e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> alerar(@PathVariable Long id, @RequestBody LocalEstoque localEstoque) {
        localEstoque.setId(id);
        try {
            LocalEstoque localEstoqueSalvo = localEstoqueService.save(localEstoque);
            return ResponseEntity.ok(localEstoqueSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao alterar o local de estoque: " + e.getMessage());
        }
    }
}
