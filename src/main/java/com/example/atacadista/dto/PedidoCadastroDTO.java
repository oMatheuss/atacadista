package com.example.atacadista.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PedidoCadastroDTO(
    @NotNull(message = "O campo cpf é obrigatório")
    @Pattern(regexp = "^[0-9]{11}$", message = "O campo cpf deve conter 11 caracteres numéricos")
    String cpfCliente,

    @NotNull(message = "O campo itens é obrigatório")
    @Size(min = 1, max = 99, message = "O número de itens deve ser maior que 0 e menor que 100")
    List<@Valid Item> itens
) {
	public record Item(
        @NotNull(message = "O campo codigoProduto é obrigatório")
        Long codigoProduto,

        @NotNull(message = "O campo quantidade é obrigatório")
        @Min(value = 1, message = "A quantidade deve ser maior que zero")
        Integer quantidade,

        @NotNull(message = "O campo valorVenda é obrigatório")
        Double valorVenda
	) {}
}
