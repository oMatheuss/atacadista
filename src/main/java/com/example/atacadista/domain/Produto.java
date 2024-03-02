package com.example.atacadista.domain;

import jakarta.persistence.*;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String descricao;

    private int tipoEmbalagem;

    @ManyToOne
    @JoinColumn(name = "codigo_categoria", referencedColumnName = "codigo")
    private Categoria categoria;

    @Column(nullable = false)
    private double preco;

    @Column(nullable = false)
    private float percentualMaximoDesconto;

    public Produto() {}

    public Produto(String descricao, double preco, float percentualMaximoDesconto) {
        this.descricao = descricao;
        this.preco = preco;
        this.percentualMaximoDesconto = percentualMaximoDesconto;
    }

    public Long getCodigo() {
        return codigo;
    }

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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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

    @Override
    public String toString() {
        return "Produto{" +
                "codigo=" + codigo +
                ", descricao='" + descricao + '\'' +
                ", tipoEmbalagem=" + tipoEmbalagem +
                ", categoria=" + categoria +
                ", preco=" + preco +
                ", percentualMaximoDesconto=" + percentualMaximoDesconto +
                '}';
    }
}
