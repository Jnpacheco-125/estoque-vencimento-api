package me.dio.service.impl;

import me.dio.controller.dto.UsuarioDTO;
import me.dio.controller.exception.ResourceNotFoundException;
import me.dio.domain.model.Usuario;
import me.dio.domain.repository.UsuarioRepository;
import me.dio.service.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public UsuarioDTO criar(UsuarioDTO dto) {
        Usuario u = new Usuario();
        u.setNome(dto.nome());
        Usuario saved = usuarioRepository.save(u);
        return toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
        return toDto(u);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioDTO dto) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
        u.setNome(dto.nome());
        Usuario updated = usuarioRepository.save(u);
        return toDto(updated);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario", id);
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO toDto(Usuario u) {
        return new UsuarioDTO(u.getId(), u.getNome());
    }
}
