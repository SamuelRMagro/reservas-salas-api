package br.com.samuelmagro.reserva_salas_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id")
    private Sala sala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private LocalDateTime inicio;
    private LocalDateTime fim;
    private Integer quantidadeParticipantes;

    @Enumerated(EnumType.STRING)
    private StatusReserva status;

    private Instant canceladaEm;
    private Instant criadaEm;
    private Instant atualizadaEm;
}
