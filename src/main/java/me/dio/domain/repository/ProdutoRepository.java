package me.dio.domain.repository;

import me.dio.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Métodos customizados:
    // Optional<Produto> findByNome(String nome);
}
