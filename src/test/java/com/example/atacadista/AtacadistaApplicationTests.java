package com.example.atacadista;

import com.example.atacadista.domain.*;
import com.example.atacadista.dto.CategoriaCadastroDTO;
import com.example.atacadista.dto.ClienteCadastroDTO;
import com.example.atacadista.dto.PedidoCadastroDTO;
import com.example.atacadista.dto.ProdutoCadastroDTO;
import com.example.atacadista.repository.CategoriaRepository;
import com.example.atacadista.repository.ClienteRepository;
import com.example.atacadista.repository.PedidoRepository;
import com.example.atacadista.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AtacadistaApplicationTests {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteRepository clienteRepository;
    @MockBean
    private CategoriaRepository categoriaRepository;
    @MockBean
    private ProdutoRepository produtoRepository;
    @MockBean
    private PedidoRepository pedidoRepository;

    @Test
    void testSaveCliente() throws Exception {
        var clienteDTO = new ClienteCadastroDTO("Matheus", "abc", "MG");

        // cpf está errado, e a requisição deve retornar status 400
        mockMvc.perform(post("/api/cliente")
                        .content(mapper.writeValueAsString(clienteDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // a função save do repositorio nunca deve ter sido chamada
        verify(clienteRepository, never()).save(any(Cliente.class));

        clienteDTO = new ClienteCadastroDTO("Matheus", "12345678901", "MG");

        // tudo ok, deve retornar 201
        mockMvc.perform(post("/api/cliente")
                        .content(mapper.writeValueAsString(clienteDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        var model = new Cliente(clienteDTO.nome(), clienteDTO.cpf(), clienteDTO.uf());

        // a função save do jpa deve ter sido chamada apenas uma vez, com as informações passadas
        verify(clienteRepository, times(1)).save(Mockito.eq(model));
    }

    @Test
    void testSaveCategoria() throws Exception {
        var categoriaDTO = new CategoriaCadastroDTO((String)null);

        // descricao null, deve retornar status 400
        mockMvc.perform(post("/api/categoria")
                        .content(mapper.writeValueAsString(categoriaDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        categoriaDTO = new CategoriaCadastroDTO("bazar");

        // descricao ok, deve retornar 201
        mockMvc.perform(post("/api/categoria")
                        .content(mapper.writeValueAsString(categoriaDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        var model = new Categoria(categoriaDTO.descricao());

        // funcao save chamada uma vez com os dados enviados
        verify(categoriaRepository, times(1)).save(eq(model));
    }

    @Test
    void testSaveProduto() throws Exception {
        var produtoDTO = new ProdutoCadastroDTO("", 0, 0L, 0, 500);

        // dados inválidos, deve retornar status 400
        mockMvc.perform(post("/api/produto")
                        .content(mapper.writeValueAsString(produtoDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        produtoDTO = new ProdutoCadastroDTO("liquidificador", 1, 1L, 110, 9);

        var categoria = new Categoria("bazar");
        // retornar categoria quando o codigoCategoria for 1
        when(categoriaRepository.findById(eq(1L))).thenReturn(Optional.of(categoria));

        // tudo ok, deve retornar 201
        mockMvc.perform(post("/api/produto")
                        .content(mapper.writeValueAsString(produtoDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        var model = new Produto(produtoDTO.descricao(), produtoDTO.preco(), produtoDTO.percentualMaximoDesconto());
        model.setCategoria(categoria);
        model.setTipoEmbalagem(produtoDTO.tipoEmbalagem());

        // funcao save chamada uma vez com os dados enviados
        verify(produtoRepository, times(1)).save(eq(model));
    }

    @Test
    void testSavePedido() throws Exception {
        var itemDTO = new PedidoCadastroDTO.Item(1L, 10, 100.99D);
        var pedidoDTO = new PedidoCadastroDTO("12345678901", List.of(itemDTO));

        var produtoModel = new Produto("liquidificador", 110D, 9F);
        // retorna produto quando o codigoProduto for 1
        when(produtoRepository.findById(eq(1L))).thenReturn(Optional.of(produtoModel));

        var clienteModel = new Cliente("Matheus", "12345678901", "MG");
        // retorna produto quando o cpf for 12345678901
        when(clienteRepository.findByCpf(eq("12345678901"))).thenReturn(Optional.of(clienteModel));

        // tudo ok, status deve ser 201
        mockMvc.perform(post("/api/pedido")
                        .content(mapper.writeValueAsString(pedidoDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        // criando objeto que deveria ter sido salvo
        var pedidoModel = getPedidoModel(produtoModel, clienteModel, itemDTO);

        // função save deve ter sido chamada uma vez com os dados enviados
        verify(pedidoRepository, times(1)).save(eq(pedidoModel));
    }

    private static Pedido getPedidoModel(Produto produtoModel, Cliente clienteModel, PedidoCadastroDTO.Item itemDTO) {
        var itemModel = new ItemPedido(1, produtoModel);
        itemModel.setValorVenda(itemDTO.valorVenda());
        itemModel.setQuantidade(itemDTO.quantidade());
        var valorTotal = itemDTO.valorVenda() * itemDTO.quantidade();
        itemModel.setValorTotal(valorTotal);
        itemModel.setPercentualDesconto((1F - (float) (itemDTO.valorVenda() / produtoModel.getPreco())) * 100F);

        var pedidoModel = new Pedido(clienteModel, valorTotal);
        pedidoModel.addItem(itemModel);
        return pedidoModel;
    }
}
