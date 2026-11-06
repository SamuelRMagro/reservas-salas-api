package br.com.samuelmagro.reserva_salas_api.specification;

import br.com.samuelmagro.reserva_salas_api.model.Usuario;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UsuarioSpecification {

    public static Specification<Usuario> nomeContem(String nome) {
        return (root, query, criteriaBuilder) ->
                nome == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Usuario> emailContem(String email) {
        return (root, query, criteriaBuilder) ->
                email == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<Usuario> ativo(Boolean ativo) {
        return (root, query, criteriaBuilder) ->
                ativo == null ? null : criteriaBuilder.equal(root.get("ativo"), ativo);
    }

    public static Specification<Usuario> criadoDepoisDe(LocalDate criadoDepois) {
        return (root, query, criteriaBuilder) -> {
            if (criadoDepois == null) return null;

            LocalDateTime inicioDateTime = criadoDepois != null ? criadoDepois.atStartOfDay() : null;

            return criteriaBuilder.greaterThanOrEqualTo(root.get("criadoEm"), inicioDateTime);
        };
    }

    public static Specification<Usuario> criadoAte(LocalDate criadoAte) {
        return (root, query, criteriaBuilder) -> {
            if (criadoAte == null) return null;

            LocalDateTime fimDateTime = criadoAte != null ? criadoAte.plusDays(1).atStartOfDay() : null;

            return criteriaBuilder.lessThan(root.get("criadoEm"), fimDateTime);
        };
    }
}
