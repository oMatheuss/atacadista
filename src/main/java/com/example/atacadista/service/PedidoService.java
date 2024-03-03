package com.example.atacadista.service;

import com.example.atacadista.domain.ItemPedido;
import com.example.atacadista.domain.Pedido;
import com.example.atacadista.dto.PedidoCadastroDTO;
import com.example.atacadista.exception.BusinessException;
import com.example.atacadista.repository.ClienteRepository;
import com.example.atacadista.repository.PedidoRepository;
import com.example.atacadista.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

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

    public Pedido cadastrarPedido(PedidoCadastroDTO dto) {
        var cliente = clienteRepository.findByCpf(dto.getCpfCliente())
                .orElseThrow(() -> new BusinessException(
                        String.format("cpf informado (%s) não cadastrado", dto.getCpfCliente())));

        var pedido = new Pedido(cliente, 0);

        int numeroItem = 1;
        double valorTotal = 0D;

        for (var item : dto.getItens()) {
            int _numeroItem = numeroItem;

            var produto = produtoRepository.findById(item.getCodigoProduto())
                    .orElseThrow(() -> new BusinessException(
                            String.format("item %d: codigo (%d) informado não existe",
                                    _numeroItem, item.getCodigoProduto())));

            double preco = produto.getPreco();
            double valorVenda = item.getValorVenda();

            float descontoMaximo = produto.getPercentualMaximoDesconto() / 100F;
            float desconto = 1F - (float) (valorVenda / preco);

            if (desconto > descontoMaximo) {
                throw new BusinessException(String.format("item %d: desconto maior que o permitido (%.4f)",
                        _numeroItem, produto.getPercentualMaximoDesconto()));
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
