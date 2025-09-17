package me.dio.service;

import me.dio.controller.dto.ProdutoDTO;

import java.util.List;

public interface ProdutoService {
    ProdutoDTO criar(ProdutoDTO dto);
    ProdutoDTO buscarPorId(Long id);
    List<ProdutoDTO> listarTodos();
    ProdutoDTO atualizar(Long id, ProdutoDTO dto);
    void deletar(Long id);
}
