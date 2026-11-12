package br.com.samuelmagro.reserva_salas_api.service;

import br.com.samuelmagro.reserva_salas_api.dto.sala.SalaCreateDTO;
import br.com.samuelmagro.reserva_salas_api.dto.sala.SalaFilterRequest;
import br.com.samuelmagro.reserva_salas_api.dto.sala.SalaResponseDTO;
import br.com.samuelmagro.reserva_salas_api.dto.sala.SalaUpdateDTO;
import br.com.samuelmagro.reserva_salas_api.exception.custom.RegistroInativoCustomException;
import br.com.samuelmagro.reserva_salas_api.exception.custom.RegistroDuplicadoCustomException;
import br.com.samuelmagro.reserva_salas_api.exception.custom.RegistroNaoEncontradoCustomException;
import br.com.samuelmagro.reserva_salas_api.mapper.SalaMapper;
import br.com.samuelmagro.reserva_salas_api.model.Sala;
import br.com.samuelmagro.reserva_salas_api.repository.SalaRepository;
import br.com.samuelmagro.reserva_salas_api.specification.SalaSpecification;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class SalaService {

    private final SalaRepository salaRepository;
    private final SalaMapper salaMapper;

    public SalaService(SalaRepository salaRepository, SalaMapper salaMapper) {
        this.salaRepository = salaRepository;
        this.salaMapper = salaMapper;
    }

    public Page<SalaResponseDTO> findAll(Pageable pageable, @Valid SalaFilterRequest request) {
        Specification<Sala> spec = Specification
                .where(SalaSpecification.nomeContem(request.nome()))
                .and(SalaSpecification.capacidadeAte(request.capacidade()))
                .and(SalaSpecification.localizacaoContem(request.localizacao()))
                .and(SalaSpecification.ativa(request.ativa()))
                .and(SalaSpecification.criadaDepoisDe(request.criadaDepoisDe()))
                .and(SalaSpecification.criadaAte(request.criadaAte()));
        Page<Sala> salaPage = salaRepository.findAll(spec, pageable);
        return salaPage.map(salaMapper::fromEntityToDTO);
    }

    public SalaResponseDTO findById(Long id) {
        Sala sala = findSalaById(id);
        return salaMapper.fromEntityToDTO(sala);
    }

    @Transactional
    public SalaResponseDTO create(@Valid SalaCreateDTO createDTO) {
        Sala sala = Sala.builder()
                .nome(createDTO.nome())
                .capacidade(createDTO.capacidade())
                .localizacao(createDTO.localizacao())
                .ativa(true)
                .criadaEm(Instant.now())
                .build();

        if(salaRepository.existsByNome(sala.getNome())) {
            throw new RegistroDuplicadoCustomException("Sala já existe.");
        }

        return salaMapper.fromEntityToDTO(salaRepository.save(sala));
    }

    @Transactional
    public SalaResponseDTO update(@Valid SalaUpdateDTO updateDTO, Long id) {
        Sala sala = findSalaById(id);

        if (salaRepository.existsByNomeAndIdNot(updateDTO.nome(), id)) {
            throw new RegistroDuplicadoCustomException("Sala já existe.");
        }

        sala.setNome(updateDTO.nome());
        sala.setCapacidade(updateDTO.capacidade());
        sala.setLocalizacao(updateDTO.localizacao());
        sala.setAtiva(true);
        sala.setAtualizadaEm(Instant.now());
        return salaMapper.fromEntityToDTO(salaRepository.save(sala));
    }

    @Transactional
    public void delete(Long id) {
        Sala sala = findSalaById(id);

        if (!sala.getAtiva()) {
            return;
        } else {
            sala.setAtiva(false);
            sala.setAtualizadaEm(Instant.now());
            salaRepository.save(sala);
        }
    }

    private Sala findSalaById(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoCustomException(String.format("Sala de id: %d não foi encontrada.", id)));
    }

    public Sala findByIdAndAtivo(Long id) {
        return salaRepository.findByIdAndAtivaTrue(id)
                .orElseThrow(() -> new RegistroInativoCustomException(String.format("Sala de id: %d não foi encontrada ou está inativa.", id)));
    }
}
