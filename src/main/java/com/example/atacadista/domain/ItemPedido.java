package com.example.atacadista.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(ItemPedido.ItemPedidoPK.class)
public class ItemPedido {

    @Id
    private Integer numeroItem;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "codigo_pedido", nullable = false)
    @JsonIgnore
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "codigo_produto", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private double valorVenda;

    @Column(nullable = false)
    private double valorTotal;

    @Column(nullable = false)
    private float percentualDesconto;

    public ItemPedido() {}

    public ItemPedido(Integer numeroItem, Produto produto) {
        this.numeroItem = numeroItem;
        this.produto = produto;
    }

    public Integer getNumeroItem() {
        return numeroItem;
    }

    public void setNumeroItem(Integer numeroItem) {
        this.numeroItem = numeroItem;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public float getPercentualDesconto() {
        return percentualDesconto;
    }

    public void setPercentualDesconto(float percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
    }

    public static class ItemPedidoPK implements Serializable {
        protected Integer numeroItem;
        protected Pedido pedido;

        public ItemPedidoPK() {}

        public ItemPedidoPK(Integer numeroItem, Pedido pedido) {
            this.numeroItem = numeroItem;
            this.pedido = pedido;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ItemPedidoPK that = (ItemPedidoPK) o;
            return Objects.equals(numeroItem, that.numeroItem) && Objects.equals(pedido, that.pedido);
        }

        @Override
        public int hashCode() {
            return Objects.hash(numeroItem, pedido);
        }
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "numeroItem=" + numeroItem +
                ", produto=" + produto +
                ", quantidade=" + quantidade +
                ", valorVenda=" + valorVenda +
                ", valorTotal=" + valorTotal +
                ", percentualDesconto=" + percentualDesconto +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedido that = (ItemPedido) o;
        return quantidade == that.quantidade && Double.compare(valorVenda, that.valorVenda) == 0 && Double.compare(valorTotal, that.valorTotal) == 0 && Float.compare(percentualDesconto, that.percentualDesconto) == 0 && Objects.equals(numeroItem, that.numeroItem) && Objects.equals(produto, that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroItem, produto, quantidade, valorVenda, valorTotal, percentualDesconto);
    }
}
