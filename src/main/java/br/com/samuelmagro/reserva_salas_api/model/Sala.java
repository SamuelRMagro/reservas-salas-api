package br.com.samuelmagro.reserva_salas_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_salas")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer capacidade;
    private String localizacao;
    private Boolean ativa;
    private Instant criadaEm;
    private Instant atualizadaEm;
}
