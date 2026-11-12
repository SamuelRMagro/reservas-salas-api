package br.com.samuelmagro.reserva_salas_api.service;

import br.com.samuelmagro.reserva_salas_api.dto.reserva.*;
import br.com.samuelmagro.reserva_salas_api.exception.custom.*;
import br.com.samuelmagro.reserva_salas_api.mapper.ReservaMapper;
import br.com.samuelmagro.reserva_salas_api.model.Reserva;
import br.com.samuelmagro.reserva_salas_api.model.Sala;
import br.com.samuelmagro.reserva_salas_api.model.StatusReserva;
import br.com.samuelmagro.reserva_salas_api.model.Usuario;
import br.com.samuelmagro.reserva_salas_api.repository.ReservaRepository;
import br.com.samuelmagro.reserva_salas_api.specification.ReservaSpecification;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;
    private final UsuarioService usuarioService;
    private final SalaService salaService;

    public ReservaService(ReservaRepository reservaRepository, ReservaMapper reservaMapper, UsuarioService usuarioService, SalaService salaService) {
        this.reservaRepository = reservaRepository;
        this.reservaMapper = reservaMapper;
        this.usuarioService = usuarioService;
        this.salaService = salaService;
    }

    public Page<ReservaResponseDTO> findAll(Pageable pageable, @Valid ReservaFilterRequest request) {
        Specification<Reserva> spec = Specification
                .where(ReservaSpecification.usuarioContem(request.usuario()))
                .and(ReservaSpecification.salaContem(request.sala()))
                .and(ReservaSpecification.inicioAPartirDe(request.inicio()))
                .and(ReservaSpecification.fimAte(request.fim()))
                .and(ReservaSpecification.comStatus(request.status()))
                .and(ReservaSpecification.criadaDepoisDe(request.criadaDepoisDe()))
                .and(ReservaSpecification.criadaAte(request.criadaAte()));
        Page<Reserva> reservaPage = reservaRepository.findAll(spec, pageable);
        return reservaPage.map(reservaMapper::fromEntityToDTO);
    }

    public ReservaResponseDTO findById(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoCustomException(String.format("Reserva com id: %d não foi encontrada.", id)));
        return reservaMapper.fromEntityToDTO(reserva);
    }

    @Transactional
    public ReservaResponseDTO create(@Valid ReservaCreateDTO createDTO) {
        Usuario usuario = usuarioService.findUsuarioByIdAndAtivo(createDTO.usuarioId());
        Sala sala = salaService.findByIdAndAtivo(createDTO.salaId());

        validaParticipantesHorarioESala(createDTO.quantidadeParticipantes(), sala.getCapacidade(), createDTO.inicio(), createDTO.fim(), createDTO.salaId(), null);

        Reserva reserva = Reserva.builder()
                .sala(sala)
                .usuario(usuario)
                .inicio(createDTO.inicio())
                .fim(createDTO.fim())
                .quantidadeParticipantes(createDTO.quantidadeParticipantes())
                .status(StatusReserva.ATIVA)
                .criadaEm(Instant.now())
                .build();
        return reservaMapper.fromEntityToDTO(reservaRepository.save(reserva));
    }

    @Transactional
    public ReservaResponseDTO update(@Valid ReservaUpdateDTO updateDTO, Long id) {
        Reserva reserva = reservaRepository.findByIdAndStatusEquals(id, StatusReserva.ATIVA)
                .orElseThrow(() -> new RegistroInativoCustomException(String.format("Reserva com id: %d não foi encontrada ou está inativa.", id)));
        Sala sala = salaService.findByIdAndAtivo(updateDTO.salaId());

        validaParticipantesHorarioESala(updateDTO.quantidadeParticipantes(), sala.getCapacidade(), updateDTO.inicio(), updateDTO.fim(), updateDTO.salaId(), id);

        reserva.setSala(sala);
        reserva.setInicio(updateDTO.inicio());
        reserva.setFim(updateDTO.fim());
        reserva.setQuantidadeParticipantes(updateDTO.quantidadeParticipantes());
        reserva.setAtualizadaEm(Instant.now());
        return reservaMapper.fromEntityToDTO(reservaRepository.save(reserva));
    }

    @Transactional
    public void cancelar(Long id) {
        Reserva reserva = reservaRepository.findByIdAndStatusEquals(id, StatusReserva.ATIVA)
                .orElseThrow(() -> new AtualizacaoInvalidaCustomException(String.format("Reserva com id: %d não foi encontrada ou já está inativa.", id)));

        reserva.setStatus(StatusReserva.CANCELADA);
        reserva.setCanceladaEm(Instant.now());
        reserva.setAtualizadaEm(Instant.now());
        reservaRepository.save(reserva);
    }

    @Transactional
    public ReservaResponseDTO reativar(Long id) {
        Reserva reserva = reservaRepository.findByIdAndStatusEquals(id, StatusReserva.CANCELADA)
                .orElseThrow(() -> new AtualizacaoInvalidaCustomException(String.format("Reserva com id: %d não foi encontrada ou já está ativa.", id)));

        if (!reserva.getSala().getAtiva()){
            throw new AtualizacaoInvalidaCustomException(String.format("A sala com id: %d está inativa.", reserva.getSala().getId()));
        }

        validarHorario(reserva.getInicio(), reserva.getFim());

        if (reservaRepository.conflitoDeHorario(reserva.getSala().getId(), reserva.getInicio(), reserva.getFim(), null)) {
            throw new ConflitoHorarioCustomException("A sala já possui uma reserva ativa nesse período.");
        }

        reserva.setStatus(StatusReserva.ATIVA);
        reserva.setAtualizadaEm(Instant.now());
        reserva.setCanceladaEm(null);
        return reservaMapper.fromEntityToDTO(reservaRepository.save(reserva));
    }

    private void validarHorario(LocalDateTime inicio, LocalDateTime fim) {
        final LocalTime INICIO_EXPEDIENTE = LocalTime.of(7, 0);
        final LocalTime FIM_EXPEDIENTE = LocalTime.of(22, 0);
        final LocalDateTime AGORA = LocalDateTime.now();

        if (!inicio.toLocalDate().equals(fim.toLocalDate())) {
            throw new ForaHorarioPermitidoCustomException("A reserva deve ser feita no mesmo dia.");
        }

        if (inicio.toLocalTime().isBefore(INICIO_EXPEDIENTE) || fim.toLocalTime().isAfter(FIM_EXPEDIENTE)) {
            throw new ForaHorarioPermitidoCustomException("A reserva deve ocorrer entre 07:00 e 22:00.");
        }

        if (fim.isBefore(inicio) || Duration.between(inicio, fim).toMinutes() < 15) {
            throw new PermanenciaMinimaCustomExcepion("A reserva deve ter uma permanência mínima de 15 minutos.");
        }

        if (inicio.isBefore(AGORA.plusMinutes(15))) {
            throw new AntecedenciaInsuficienteCustomException("A reserva deve ser feita com antecedência mínima de 15 minutos.");
        }
    }

    private void validaParticipantesHorarioESala(Integer qntParticipantes, Integer capacidadeSala, LocalDateTime inicio, LocalDateTime fim, Long salaId, Long reservaId) {
        if (qntParticipantes > capacidadeSala) {
            throw new CapacidadeExcedidaCustomException("A quantidade de participantes excede a capacidade da sala.");
        }

        validarHorario(inicio, fim);

        if (reservaRepository.conflitoDeHorario(salaId,inicio, fim, reservaId)) {
            throw new ConflitoHorarioCustomException("A sala já possui uma reserva ativa nesse período.");
        }
    }
}
