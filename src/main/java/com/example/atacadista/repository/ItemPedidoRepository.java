package com.example.atacadista.repository;

import com.example.atacadista.domain.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedido.ItemPedidoPK> {}
