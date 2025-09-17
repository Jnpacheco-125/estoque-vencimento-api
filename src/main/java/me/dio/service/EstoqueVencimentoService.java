package me.dio.service;


import me.dio.controller.dto.EstoqueVencimentoDTO;
import me.dio.domain.model.EstoqueVencimento;

import java.time.LocalDate;
import java.util.List;

public interface EstoqueVencimentoService {

    EstoqueVencimentoDTO criar(EstoqueVencimentoDTO dto);
    EstoqueVencimentoDTO buscarPorId(Long id);
    List<EstoqueVencimentoDTO> listarTodos();
    EstoqueVencimentoDTO atualizar(Long id, EstoqueVencimentoDTO dto);
    void deletar(Long id);
    List<EstoqueVencimentoDTO> listarProximosVencimentos();
    List<EstoqueVencimentoDTO> listarVencidos();
}
