package com.controle_estoque.controller;

import com.controle_estoque.dto.ReceitaDTO;
import com.controle_estoque.dto.ReceitaRetornoDTO;
import com.controle_estoque.model.ItemReceita;
import com.controle_estoque.model.LocalEstoque;
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

    @PutMapping("/{id}")
    public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody ReceitaDTO receitaDTO) {
        try {
            ReceitaRetornoDTO receitaRetornoDTO = receitaService.altera(id, receitaDTO);
            return ResponseEntity.ok(receitaRetornoDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao alterar a receita: " + e.getMessage());
        }
    }

    @GetMapping
    public List<ReceitaRetornoDTO> listar() {
        return receitaService.listarTodasReceitas();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            Receita receitaRetorno = receitaService.findById(id.longValue());
            if(receitaRetorno == null) {
                return ResponseEntity.notFound().build();
            } else {
                receitaService.delete(receitaRetorno);
                return ResponseEntity.ok(receitaRetorno);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deletar a receita: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReceitaCompleta(@PathVariable Long id) {
        try {
            ReceitaRetornoDTO receitaRetornoDTO = receitaService.listarReceita(id);
            return ResponseEntity.ok(receitaRetornoDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao salvar ao buscar a receita: " + e.getMessage());
        }
    }
}
