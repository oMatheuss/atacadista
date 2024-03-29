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
        var cliente = clienteRepository.findByCpf(dto.cpfCliente())
                .orElseThrow(() -> new BusinessException(
                        String.format("cpf informado (%s) não cadastrado", dto.cpfCliente())));

        var pedido = new Pedido(cliente, 0);

        int numeroItem = 1;
        double valorTotal = 0D;

        for (var item : dto.itens()) {
            int finalNumeroItem = numeroItem;

            var produto = produtoRepository.findById(item.codigoProduto())
                    .orElseThrow(() -> new BusinessException(
                            String.format("item %d: codigo (%d) informado não existe",
                                    finalNumeroItem, item.codigoProduto())));

            double preco = produto.getPreco();
            double valorVenda = item.valorVenda();

            float descontoMaximo = produto.getPercentualMaximoDesconto() / 100F;
            float desconto = 1F - (float) (valorVenda / preco);

            if (desconto > descontoMaximo) {
                throw new BusinessException(String.format("item %d: desconto maior que o permitido (%.4f)",
                        finalNumeroItem, produto.getPercentualMaximoDesconto()));
            }

            var valorTotalItem = item.valorVenda() * item.quantidade();
            valorTotal += valorTotalItem;

            var itemPedido = new ItemPedido(numeroItem++, produto);
            itemPedido.setValorVenda(valorVenda);
            itemPedido.setQuantidade(item.quantidade());
            itemPedido.setValorTotal(valorTotalItem);
            itemPedido.setPercentualDesconto(desconto * 100F);

            pedido.addItem(itemPedido);
        }

        pedido.setValorTotal(valorTotal);

        return pedidoRepository.save(pedido);
    }
}
