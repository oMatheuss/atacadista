package com.example.atacadista;

import com.example.atacadista.domain.Cliente;
import com.example.atacadista.repository.ClienteRepository;
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
    void testSaveCliente() throws Exception {
        // cpf está errado, e a requisição deve retornar status 400
        mockMvc.perform(post("/api/cliente")
                        .content("{ \"cpf\": \"abc\", \"nome\": \"Matheus\", \"uf\": \"MG\" }")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // tudo ok, deve retornar 201
        mockMvc.perform(post("/api/cliente")
                        .content("{ \"cpf\": \"12345678912\", \"nome\": \"Matheus\", \"uf\": \"MG\" }")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        // a função save do jpa deve ter sido chamada uma vez
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

}
