package com.example.atacadista.domain;

import jakarta.persistence.*;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String nome;

    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(11)")
    private String cpf;

    @Column(nullable = false, columnDefinition = "VARCHAR(2)")
    private String uf;

    public Cliente() {}

    public Cliente(String nome, String cpf, String uf) {
        this.nome = nome;
        this.cpf = cpf;
        this.uf = uf;
    }

    public Long getCodigo() {
        return codigo;
    }

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

    @Override
    public String toString() {
        return "Cliente{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", uf='" + uf + '\'' +
                '}';
    }
}
