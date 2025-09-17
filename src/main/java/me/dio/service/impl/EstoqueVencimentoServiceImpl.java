package me.dio.service.impl;

import me.dio.controller.dto.EstoqueVencimentoDTO;
import me.dio.controller.dto.ProdutoDTO;
import me.dio.controller.dto.UsuarioDTO;
import me.dio.controller.exception.ResourceNotFoundException;
import me.dio.domain.model.EstoqueVencimento;
import me.dio.domain.model.Produto;
import me.dio.domain.model.Usuario;
import me.dio.domain.repository.EstoqueVencimentoRepository;
import me.dio.domain.repository.ProdutoRepository;
import me.dio.domain.repository.UsuarioRepository;
import me.dio.service.EstoqueVencimentoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstoqueVencimentoServiceImpl implements EstoqueVencimentoService {
    private final EstoqueVencimentoRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    public EstoqueVencimentoServiceImpl(
            EstoqueVencimentoRepository estoqueRepository,
            ProdutoRepository produtoRepository,
            UsuarioRepository usuarioRepository) {
        this.estoqueRepository = estoqueRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public EstoqueVencimentoDTO criar(EstoqueVencimentoDTO dto) {
        // buscar produto e usuario (por id vindo no DTO)
        Long produtoId = dto.produto() != null ? dto.produto().id() : null;
        Long usuarioId = dto.usuario() != null ? dto.usuario().id() : null;

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", produtoId));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", usuarioId));

        EstoqueVencimento e = new EstoqueVencimento();
        e.setProduto(produto);
        e.setUsuario(usuario);
        e.setQuantidade(dto.quantidade());
        e.setDataEntrada(dto.dataEntrada());
        e.setDataVencimento(dto.dataVencimento());

        EstoqueVencimento saved = estoqueRepository.save(e);
        return toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public EstoqueVencimentoDTO buscarPorId(Long id) {
        EstoqueVencimento e = estoqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EstoqueVencimento", id));
        return toDto(e);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstoqueVencimentoDTO> listarTodos() {
        return estoqueRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EstoqueVencimentoDTO atualizar(Long id, EstoqueVencimentoDTO dto) {
        EstoqueVencimento e = estoqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EstoqueVencimento", id));

        if (dto.produto() != null && dto.produto().id() != null) {
            Produto produto = produtoRepository.findById(dto.produto().id())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto", dto.produto().id()));
            e.setProduto(produto);
        }
        if (dto.usuario() != null && dto.usuario().id() != null) {
            Usuario usuario = usuarioRepository.findById(dto.usuario().id())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario", dto.usuario().id()));
            e.setUsuario(usuario);
        }

        e.setQuantidade(dto.quantidade());
        e.setDataEntrada(dto.dataEntrada());
        e.setDataVencimento(dto.dataVencimento());

        EstoqueVencimento updated = estoqueRepository.save(e);
        return toDto(updated);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (!estoqueRepository.existsById(id)) {
            throw new ResourceNotFoundException("EstoqueVencimento", id);
        }
        estoqueRepository.deleteById(id);
    }

    @Override
    public List<EstoqueVencimentoDTO> listarProximosVencimentos() {
        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(10);

        return estoqueRepository.proximosVencimentos(hoje, limite)
                .stream()
                .map(this::toDtoComDias)
                .collect(Collectors.toList());
    }

    @Override
    public List<EstoqueVencimentoDTO> listarVencidos() {
        LocalDate hoje = LocalDate.now();

        return estoqueRepository.findByDataVencimentoBefore(hoje)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    private EstoqueVencimentoDTO toDto(EstoqueVencimento e) {
        Produto p = e.getProduto();
        Usuario u = e.getUsuario();

        ProdutoDTO pDto = new ProdutoDTO(p.getId(), p.getNome());
        UsuarioDTO uDto = new UsuarioDTO(u.getId(), u.getNome());

        long diasParaVencer = ChronoUnit.DAYS.between(LocalDate.now(), e.getDataVencimento());

        return new EstoqueVencimentoDTO(
                e.getId(),
                pDto,
                uDto,
                e.getQuantidade(),
                e.getDataEntrada(),
                e.getDataVencimento(),
                diasParaVencer
        );
    }

    private EstoqueVencimentoDTO toDtoComDias(EstoqueVencimento e) {
        ProdutoDTO pDto = new ProdutoDTO(e.getProduto().getId(), e.getProduto().getNome());
        UsuarioDTO uDto = new UsuarioDTO(e.getUsuario().getId(), e.getUsuario().getNome());

        long diasParaVencer = ChronoUnit.DAYS.between(LocalDate.now(), e.getDataVencimento());

        return new EstoqueVencimentoDTO(
                e.getId(),
                pDto,
                uDto,
                e.getQuantidade(),
                e.getDataEntrada(),
                e.getDataVencimento(),
                diasParaVencer
        );
    }



}

