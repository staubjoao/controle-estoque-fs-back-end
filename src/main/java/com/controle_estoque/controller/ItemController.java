package com.controle_estoque.controller;

import com.controle_estoque.model.Item;
import com.controle_estoque.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<Item> listar() {
        return itemService.getAll();
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody Item item) {
        try {
            Item itemSalvo = itemService.save(item);
            return ResponseEntity.ok(itemSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao salvar o item: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        try {
            Item item = itemService.findById(id.longValue());
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
            Item item = itemService.findById(id.longValue());
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
}
