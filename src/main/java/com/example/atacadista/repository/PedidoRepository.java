package com.example.atacadista.repository;

import com.example.atacadista.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query(value = """
            SELECT p
            FROM Pedido p
            INNER JOIN Cliente c
                ON c.codigo = p.cliente.codigo
            WHERE c.cpf = :cpf
            """)
    List<Pedido> findByCliente(String cpf);
}
