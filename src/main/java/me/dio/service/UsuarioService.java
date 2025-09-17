package me.dio.service;

import me.dio.controller.dto.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO criar(UsuarioDTO dto);
    UsuarioDTO buscarPorId(Long id);
    List<UsuarioDTO> listarTodos();
    UsuarioDTO atualizar(Long id, UsuarioDTO dto);
    void deletar(Long id);
}