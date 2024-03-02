package com.example.atacadista.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ClienteCadastroDTO {

    @NotNull(message = "O campo nome é obrigatório")
    @Size(min = 3, max = 50, message = "O campo nome deve conter entre 3 e 50 caracteres")
    private String nome;

    @NotNull(message = "O campo cpf é obrigatório")
    @Pattern(regexp = "^[0-9]{11}$", message = "O campo cpf deve conter 11 caracteres numéricos")
    private String cpf;

    @NotNull(message = "O campo uf é obrigatório")
    @Pattern(regexp = "^[A-Z]{2}$", message = "O campo uf deve conter 2 caracteres maiúsculos")
    private String uf;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
