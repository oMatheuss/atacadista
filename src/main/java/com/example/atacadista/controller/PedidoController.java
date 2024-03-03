package com.example.atacadista.controller;

import com.example.atacadista.domain.Pedido;
import com.example.atacadista.dto.PedidoCadastroDTO;
import com.example.atacadista.repository.PedidoRepository;
import com.example.atacadista.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido novoPedido(@Valid @RequestBody PedidoCadastroDTO dto) throws Exception {
        return pedidoService.cadastrarPedido(dto);
    }
}
