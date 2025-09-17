package me.dio.domain.repository;

import me.dio.domain.model.EstoqueVencimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EstoqueVencimentoRepository extends JpaRepository<EstoqueVencimento, Long> {

    @Query("SELECT e FROM EstoqueVencimento e " +
            "WHERE e.dataVencimento BETWEEN :start AND :end " +
            "ORDER BY e.dataVencimento ASC")
    List<EstoqueVencimento> proximosVencimentos(@Param("start") LocalDate start,
                                                @Param("end") LocalDate end);

    List<EstoqueVencimento> findByDataVencimentoBefore(LocalDate date);
}
