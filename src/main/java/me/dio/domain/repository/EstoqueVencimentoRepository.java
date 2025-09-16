package me.dio.domain.repository;

import me.dio.domain.model.EstoqueVencimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EstoqueVencimentoRepository extends JpaRepository<EstoqueVencimento, Long> {

    // Método customizado para buscar itens que vencem nos próximos 10 dias
    List<EstoqueVencimento> findByDataVencimentoBetween(LocalDate start, LocalDate end);
}
