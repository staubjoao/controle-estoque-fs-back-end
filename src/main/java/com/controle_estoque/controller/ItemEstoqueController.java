package com.controle_estoque.controller;

import com.controle_estoque.dto.ItemEstoqueDTO;
import com.controle_estoque.model.ItemEstoque;
import com.controle_estoque.model.LocalEstoque;
import com.controle_estoque.service.ItemEstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item-estoque")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ItemEstoqueController {

    @Autowired
    private ItemEstoqueService itemService;

    @GetMapping
    public List<ItemEstoque> listar() {
        return itemService.getAll();
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody ItemEstoqueDTO itemEstoqueDTO) {
        try {
            ItemEstoque itemSalvo = itemService.save(itemEstoqueDTO);
            return ResponseEntity.ok(itemSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao salvar o item: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        try {
            ItemEstoque item = itemService.findById(id.longValue());
            if(item == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(item);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar o item: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            ItemEstoque item = itemService.findById(id.longValue());
            if(item == null) {
                return ResponseEntity.notFound().build();
            } else {
                itemService.delete(item);
                return ResponseEntity.ok(item);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deletar o item: " + e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> alerar(@PathVariable Long id, @RequestBody ItemEstoque itemEstoque) {
        itemEstoque.setId(id);
        try {
            ItemEstoque itemEstoqueAlterado = itemService.alterar(itemEstoque);
            return ResponseEntity.ok(itemEstoqueAlterado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao alterar o local de estoque: " + e.getMessage());
        }
    }
}
