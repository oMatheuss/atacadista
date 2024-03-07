package com.example.atacadista.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "codigo_cliente", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Set<ItemPedido> itens = new HashSet<>();

    @Column(nullable = false)
    private double valorTotal;

    public Pedido() {}

    public Pedido(Cliente cliente, double valorTotal) {
        this.cliente = cliente;
        this.valorTotal = valorTotal;
    }

    public Long getCodigo() {
        return codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(Set<ItemPedido> itens) {
        this.itens = itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void addItem(ItemPedido item) {
        item.setPedido(this);
        itens.add(item);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "codigo=" + codigo +
                ", cliente=" + cliente +
                ", itens=" + itens +
                ", valorTotal=" + valorTotal +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Double.compare(valorTotal, pedido.valorTotal) == 0 && Objects.equals(codigo, pedido.codigo) && Objects.equals(cliente, pedido.cliente) && Objects.equals(itens, pedido.itens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, cliente, itens, valorTotal);
    }
}
