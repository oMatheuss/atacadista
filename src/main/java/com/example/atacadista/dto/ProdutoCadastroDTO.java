package com.example.atacadista.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProdutoCadastroDTO(
    @NotNull(message = "O campo descricao é obrigatório")
    @Size(max = 255, min = 3, message = "O campo descricao deve conter entre 3 e 255 caracteres")
    String descricao,

    @NotNull(message = "O campo tipoEmbalagem é obrigatório")
    int tipoEmbalagem,

    @NotNull(message = "O campo codigoCategoria é obrigatório")
    Long codigoCategoria,

    @NotNull(message = "O campo preco é obrigatório")
    double preco,

    @Digits(integer = 3, fraction = 2, message = "O campo percentualMaximoDesconto pode variar de 0 a 100 com no máximo duas casas decimais")
    @Max(value = 100, message = "O campo percentualMaximoDesconto deve estar entre 0 e 100")
    float percentualMaximoDesconto
) {}
