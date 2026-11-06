package br.com.samuelmagro.reserva_salas_api.repository;

import br.com.samuelmagro.reserva_salas_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {
    Boolean existsByEmail(String email);

    Boolean existsByEmailAndIdNot(String email, Long id);
}
