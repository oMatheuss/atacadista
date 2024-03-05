package com.example.atacadista.controller;

import com.example.atacadista.domain.Cliente;
import com.example.atacadista.dto.ClienteCadastroDTO;
import com.example.atacadista.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private final ClienteRepository repository;

    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente novoCliente(@Valid @RequestBody ClienteCadastroDTO dto) {
        var model = new Cliente(dto.nome(), dto.cpf(), dto.uf());
        return repository.save(model);
    }

    @GetMapping
    public List<Cliente> listarClientes(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String uf
    ) {
        Cliente model = new Cliente(nome, cpf, uf);
        return repository.findAll(Example.of(model));
    }

    @GetMapping("{codigo}")
    public Cliente clientePorCodigo(@PathVariable long codigo) {
        return repository.findById(codigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
