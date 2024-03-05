package com.example.atacadista.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoriaCadastroDTO(
    @NotNull(message = "O campo descricao é obrigatório")
    @Size(min = 3, max = 255, message = "O campo descricao deve conter entre 3 e 255 caracteres")
    String descricao
) {}
