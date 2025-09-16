package me.dio.domain.repository;

import me.dio.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Se quiser métodos customizados, pode declarar aqui.
    // Exemplo:
    // Optional<Usuario> findByNome(String nome);
}
