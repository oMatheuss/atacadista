package com.example.atacadista.controller;

import com.example.atacadista.domain.Produto;
import com.example.atacadista.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto novoProduto(@RequestBody Produto model) {
        return repository.save(model);
    }

    @GetMapping
    public List<Produto> listarProdutor() {
        return repository.findAll();
    }

    @DeleteMapping("{codigo}")
    @ResponseStatus(HttpStatus.OK)
    public void excluirProduto(@PathVariable Long codigo) {
        var produto = repository.findById(codigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        repository.delete(produto);
    }
}
