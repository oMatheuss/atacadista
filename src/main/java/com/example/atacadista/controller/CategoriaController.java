package com.example.atacadista.controller;

import com.example.atacadista.domain.Categoria;
import com.example.atacadista.repository.CategoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    private final CategoriaRepository repository;

    public CategoriaController(CategoriaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria novaCategoria(@RequestBody Categoria model) {
        return repository.save(model);
    }

    @GetMapping
    public List<Categoria> listarCategorias() {
        return repository.findAll();
    }
}
