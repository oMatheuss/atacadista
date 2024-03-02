package com.example.atacadista.controller;

import com.example.atacadista.domain.Categoria;
import com.example.atacadista.domain.Produto;
import com.example.atacadista.dto.ProdutoCadastroDTO;
import com.example.atacadista.dto.ProdutoQueryDTO;
import com.example.atacadista.repository.CategoriaRepository;
import com.example.atacadista.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    private final ProdutoRepository repository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoController(ProdutoRepository repository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto novoProduto(@Valid @RequestBody ProdutoCadastroDTO dto) {
        var model = new Produto(dto.getDescricao(), dto.getPreco(), dto.getPercentualMaximoDesconto());
        model.setTipoEmbalagem(dto.getTipoEmbalagem());

        var categoria = categoriaRepository.findById(dto.getCodigoCategoria())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "A categoria informada não existe"));
        model.setCategoria(categoria);

        return repository.save(model);
    }

    @GetMapping
    public List<Produto> listarProdutor(ProdutoQueryDTO dto) {
        return repository.findByDescricao(dto.getDescricaoProduto(), dto.getDescricaoCategoria());
    }

    @GetMapping("{codigo}")
    public Produto produtoPorCodigo(@PathVariable Long codigo) {
        return repository.findById(codigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{codigo}")
    @ResponseStatus(HttpStatus.OK)
    public void excluirProduto(@PathVariable Long codigo) {
        var produto = repository.findById(codigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        repository.delete(produto);
    }
}
