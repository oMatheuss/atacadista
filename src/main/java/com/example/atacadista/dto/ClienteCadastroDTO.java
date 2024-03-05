package com.example.atacadista.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteCadastroDTO(
    @NotNull(message = "O campo nome é obrigatório")
    @Size(min = 3, max = 50, message = "O campo nome deve conter entre 3 e 50 caracteres")
    String nome,

    @NotNull(message = "O campo cpf é obrigatório")
    @Pattern(regexp = "^[0-9]{11}$", message = "O campo cpf deve conter 11 caracteres numéricos")
    String cpf,

    @NotNull(message = "O campo uf é obrigatório")
    @Pattern(regexp = "^[A-Z]{2}$", message = "O campo uf deve conter 2 caracteres maiúsculos")
    String uf
) {}
