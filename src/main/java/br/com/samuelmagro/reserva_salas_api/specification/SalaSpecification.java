package br.com.samuelmagro.reserva_salas_api.specification;

import br.com.samuelmagro.reserva_salas_api.model.Sala;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SalaSpecification {

    public static Specification<Sala> nomeContem(String name){
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Sala> capacidadeAte(Integer capacidade){
        return (root, query, criteriaBuilder) ->
                capacidade == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("capacidade"), capacidade);
    }

    public static Specification<Sala> localizacaoContem(String localizacao){
        return (root, query, criteriaBuilder) ->
                localizacao == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("localizacao")), "%" + localizacao.toLowerCase() + "%");
    }

    public static Specification<Sala> ativa(Boolean ativa){
        return (root, query, criteriaBuilder) ->
                ativa == null ? null : criteriaBuilder.equal(root.get("ativa"), ativa);
    }

    public static Specification<Sala> criadaDepoisDe(LocalDate criadaDepois){
        return (root, query, criteriaBuilder) -> {
            if (criadaDepois == null) return null;

            LocalDateTime inicioDateTime = criadaDepois != null ? criadaDepois.atStartOfDay() : null;

            return criteriaBuilder.greaterThanOrEqualTo(root.get("criadoEm"), inicioDateTime);
        };
    }

    public static Specification<Sala> criadaAte(LocalDate criadaAte){
        return (root, query, criteriaBuilder) -> {
            if (criadaAte == null) return null;

            LocalDateTime fimDateTime = criadaAte != null ? criadaAte.plusDays(1).atStartOfDay() : null;

            return criteriaBuilder.lessThan(root.get("criadoEm"), fimDateTime);
        };
    }
}
