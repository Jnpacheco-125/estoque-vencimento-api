package me.dio.domain.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_estoque_vencimento")
public class EstoqueVencimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private Long quantidade;
    private LocalDate dataVencimento;
    private LocalDate dataEntrada;
}
