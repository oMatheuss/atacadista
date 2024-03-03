package com.example.atacadista.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class PedidoCadastroDTO {

    @NotNull(message = "O campo cpf é obrigatório")
    @Pattern(regexp = "^[0-9]{11}$", message = "O campo cpf deve conter 11 caracteres numéricos")
    private String cpfCliente;

    @NotNull(message = "O campo itens é obrigatório")
    @Size(min = 1, max = 99, message = "O número de itens deve ser maior que 0 e menor que 100")
    private List<@Valid Item> itens = new ArrayList<>();

    public PedidoCadastroDTO() {}

    public static class Item {

        @NotNull(message = "O campo codigoProduto é obrigatório")
        private Long codigoProduto;

        @NotNull(message = "O campo quantidade é obrigatório")
        @Min(value = 1, message = "A quantidade deve ser maior que zero")
        private Integer quantidade;

        @NotNull(message = "O campo valorVenda é obrigatório")
        private Double valorVenda;

        public Item() {}

        public Long getCodigoProduto() {
            return codigoProduto;
        }

        public void setCodigoProduto(Long codigoProduto) {
            this.codigoProduto = codigoProduto;
        }

        public Integer getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(Integer quantidade) {
            this.quantidade = quantidade;
        }

        public Double getValorVenda() {
            return valorVenda;
        }

        public void setValorVenda(Double valorVenda) {
            this.valorVenda = valorVenda;
        }
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }
}
