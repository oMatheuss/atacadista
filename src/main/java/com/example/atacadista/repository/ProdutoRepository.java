package com.example.atacadista.repository;

import com.example.atacadista.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(value = """
            SELECT p
            FROM Produto p
            INNER JOIN Categoria c
                ON c.codigo = p.categoria.codigo
                AND c.descricao LIKE :descricaoCategoria%
            WHERE p.descricao LIKE :descricao%
            """)
    List<Produto> findByDescricao(@Param("descricao") String descricao, @Param("descricaoCategoria") String descricaoCategoria);
}
