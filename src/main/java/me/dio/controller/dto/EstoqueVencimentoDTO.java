package me.dio.controller.dto;

import java.io.Serializable;
import java.time.LocalDate;

public record EstoqueVencimentoDTO(
        Long id,
        ProdutoDTO produto,
        UsuarioDTO usuario,
        Long quantidade,
        LocalDate dataEntrada,
        LocalDate dataVencimento,
        Long diasParaVencer
) implements Serializable {}
