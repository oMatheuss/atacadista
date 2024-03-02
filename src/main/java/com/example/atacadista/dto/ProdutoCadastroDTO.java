package com.example.atacadista.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProdutoCadastroDTO {

    @NotNull(message = "O campo descricao é obrigatório")
    @Size(max = 255, min = 3, message = "O campo descricao deve conter entre 3 e 255 caracteres")
    private String descricao;

    @NotNull(message = "O campo tipoEmbalagem é obrigatório")
    private int tipoEmbalagem;

    @NotNull(message = "O campo codigoCategoria é obrigatório")
    private Long codigoCategoria;

    @NotNull(message = "O campo preco é obrigatório")
    private double preco;

    private float percentualMaximoDesconto = 0;

    public ProdutoCadastroDTO() {}

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getTipoEmbalagem() {
        return tipoEmbalagem;
    }

    public void setTipoEmbalagem(int tipoEmbalagem) {
        this.tipoEmbalagem = tipoEmbalagem;
    }

    public Long getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(Long codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public float getPercentualMaximoDesconto() {
        return percentualMaximoDesconto;
    }

    public void setPercentualMaximoDesconto(float percentualMaximoDesconto) {
        this.percentualMaximoDesconto = percentualMaximoDesconto;
    }
}
