package br.com.samuelmagro.reserva_salas_api.specification;

import br.com.samuelmagro.reserva_salas_api.model.Reserva;
import br.com.samuelmagro.reserva_salas_api.model.Sala;
import br.com.samuelmagro.reserva_salas_api.model.StatusReserva;
import br.com.samuelmagro.reserva_salas_api.model.Usuario;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservaSpecification {

    public static Specification<Reserva> usuarioContem(String usuario) {
        return (root, query, criteriaBuilder) -> {
            if (usuario == null || usuario.isBlank()) {
                return null;
            }

            Join<Reserva, Usuario> usuarioJoin = root.join("usuario");
            return criteriaBuilder.like(criteriaBuilder.lower(usuarioJoin.get("nome")), "%" + usuario.toLowerCase() + "%");
        };
    }

    public static Specification<Reserva> salaContem(String sala) {
        return (root, query, criteriaBuilder) -> {
            if (sala == null || sala.isBlank()) {
                return null;
            }

            Join<Reserva, Sala> salaJoin = root.join("sala");
            return criteriaBuilder.like(criteriaBuilder.lower(salaJoin.get("nome")), "%" + sala.toLowerCase() + "%");
        };
    }

    public static Specification<Reserva> inicioAPartirDe(LocalDateTime inicio) {
        return (root, query, cb) -> inicio == null ? null
                : cb.greaterThanOrEqualTo(root.get("inicio"), inicio);
    }

    public static Specification<Reserva> fimAte(LocalDateTime fim) {
        return (root, query, cb) -> fim == null ? null
                : cb.lessThanOrEqualTo(root.get("fim"), fim);
    }

    public static Specification<Reserva> comStatus(StatusReserva status) {
        return (root, query, cb) -> status == null ? null
                : cb.equal(root.get("status"), status);
    }

    public static Specification<Reserva> criadaDepoisDe(LocalDate criadaDepois){
        return (root, query, criteriaBuilder) -> {
            if (criadaDepois == null) return null;

            LocalDateTime inicioDateTime = criadaDepois != null ? criadaDepois.atStartOfDay() : null;

            return criteriaBuilder.greaterThanOrEqualTo(root.get("criadaEm"), inicioDateTime);
        };
    }

    public static Specification<Reserva> criadaAte(LocalDate criadaAte){
        return (root, query, criteriaBuilder) -> {
            if (criadaAte == null) return null;

            LocalDateTime fimDateTime = criadaAte != null ? criadaAte.plusDays(1).atStartOfDay() : null;

            return criteriaBuilder.lessThan(root.get("criadaEm"), fimDateTime);
        };
    }
}
