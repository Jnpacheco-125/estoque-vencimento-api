package me.dio.service.impl;

import me.dio.controller.dto.ProdutoDTO;
import me.dio.controller.exception.ResourceNotFoundException;
import me.dio.domain.model.Produto;
import me.dio.domain.repository.ProdutoRepository;
import me.dio.service.ProdutoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    @Transactional
    public ProdutoDTO criar(ProdutoDTO dto) {
        Produto p = new Produto();
        p.setNome(dto.nome());
        Produto saved = produtoRepository.save(p);
        return toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProdutoDTO buscarPorId(Long id) {
        Produto p = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", id));
        return toDto(p);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProdutoDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProdutoDTO atualizar(Long id, ProdutoDTO dto) {
        Produto p = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", id));
        p.setNome(dto.nome());
        Produto updated = produtoRepository.save(p);
        return toDto(updated);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto", id);
        }
        produtoRepository.deleteById(id);
    }

    private ProdutoDTO toDto(Produto p) {
        return new ProdutoDTO(p.getId(), p.getNome());
    }
}
