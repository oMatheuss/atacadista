package com.example.atacadista;

import com.example.atacadista.domain.Cliente;
import com.example.atacadista.dto.ClienteCadastroDTO;
import com.example.atacadista.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AtacadistaApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteRepository clienteRepository;

    @Test
    void testSaveClienteWhenValid() throws Exception {
        var clienteDTO = new ClienteCadastroDTO("Matheus", "abc", "MG");

        ObjectMapper mapper = new ObjectMapper();

        // cpf está errado, e a requisição deve retornar status 400
        mockMvc.perform(post("/api/cliente")
                        .content(mapper.writeValueAsString(clienteDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        clienteDTO = new ClienteCadastroDTO("Matheus", "12345678901", "MG");

        // tudo ok, deve retornar 201
        mockMvc.perform(post("/api/cliente")
                        .content(mapper.writeValueAsString(clienteDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        // a função save do jpa deve ter sido chamada apenas uma vez
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }
}
