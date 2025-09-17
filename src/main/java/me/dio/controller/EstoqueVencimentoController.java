package me.dio.controller;

import me.dio.controller.dto.EstoqueVencimentoDTO;
import me.dio.service.EstoqueVencimentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/estoques")
public class EstoqueVencimentoController {

    private final EstoqueVencimentoService estoqueService;

    public EstoqueVencimentoController(EstoqueVencimentoService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @PostMapping
    public ResponseEntity<EstoqueVencimentoDTO> criar(@RequestBody EstoqueVencimentoDTO dto) {
        EstoqueVencimentoDTO created = estoqueService.criar(dto);
        URI location = URI.create(String.format("/api/estoques/%d", created.id()));
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueVencimentoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(estoqueService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<EstoqueVencimentoDTO>> listarTodos() {
        return ResponseEntity.ok(estoqueService.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstoqueVencimentoDTO> atualizar(
            @PathVariable Long id,
            @RequestBody EstoqueVencimentoDTO dto) {
        return ResponseEntity.ok(estoqueService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        estoqueService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/proximos-vencimentos")
    public ResponseEntity<List<EstoqueVencimentoDTO>> listarProximosVencimentos() {
        List<EstoqueVencimentoDTO> lista = estoqueService.listarProximosVencimentos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/vencidos")
    public ResponseEntity<List<EstoqueVencimentoDTO>> listarVencidos() {
        List<EstoqueVencimentoDTO> lista = estoqueService.listarVencidos();
        return ResponseEntity.ok(lista);
    }
}
