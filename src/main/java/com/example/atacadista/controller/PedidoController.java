package com.example.atacadista.controller;

import com.example.atacadista.domain.Pedido;
import com.example.atacadista.dto.PedidoCadastroDTO;
import com.example.atacadista.repository.PedidoRepository;
import com.example.atacadista.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoService pedidoService, PedidoRepository pedidoRepository) {
        this.pedidoService = pedidoService;
        this.pedidoRepository = pedidoRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido novoPedido(@Valid @RequestBody PedidoCadastroDTO dto) {
        return pedidoService.cadastrarPedido(dto);
    }

    @GetMapping("{codigo}")
    public Pedido pedidoPorCodigo(@PathVariable Long codigo) {
        return pedidoRepository.findById(codigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item inexistente"));
    }

    @GetMapping
    public List<Pedido> pedidoPorCliente(@RequestParam String cpf) {
        return pedidoRepository.findByCliente(cpf);
    }
}
