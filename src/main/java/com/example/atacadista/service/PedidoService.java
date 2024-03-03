package com.example.atacadista.service;

import com.example.atacadista.domain.ItemPedido;
import com.example.atacadista.domain.Pedido;
import com.example.atacadista.dto.PedidoCadastroDTO;
import com.example.atacadista.repository.ClienteRepository;
import com.example.atacadista.repository.ItemPedidoRepository;
import com.example.atacadista.repository.PedidoRepository;
import com.example.atacadista.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(
            PedidoRepository pedidoRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    public Pedido cadastrarPedido(PedidoCadastroDTO dto) throws Exception {
        var cliente = clienteRepository.findByCpf(dto.getCpfCliente())
                .orElseThrow(() -> new Exception("cpf informado não cadastrado"));

        var pedido = new Pedido(cliente, 0);

        int numeroItem = 1;
        double valorTotal = 0D;

        for (var item : dto.getItens()) {
            var produto = produtoRepository.findById(item.getCodigoProduto())
                    .orElseThrow(() -> new Exception("item informado não existe"));

            double preco = produto.getPreco();
            double valorVenda = item.getValorVenda();

            float descontoMaximo = produto.getPercentualMaximoDesconto() / 100F;
            float desconto = 1F - (float) (valorVenda / preco);

            if (desconto > descontoMaximo) {
                throw new Exception("desconto maior que o permitido");
            }

            var valorTotalItem = item.getValorVenda() * item.getQuantidade();
            valorTotal += valorTotalItem;

            var itemPedido = new ItemPedido(numeroItem++, produto);
            itemPedido.setValorVenda(valorVenda);
            itemPedido.setQuantidade(item.getQuantidade());
            itemPedido.setValorTotal(valorTotalItem);
            itemPedido.setPercentualDesconto(desconto * 100F);

            pedido.addItem(itemPedido);
        }

        pedido.setValorTotal(valorTotal);

        return pedidoRepository.save(pedido);
    }
}
