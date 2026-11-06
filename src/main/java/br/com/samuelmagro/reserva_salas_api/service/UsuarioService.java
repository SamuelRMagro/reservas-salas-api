package br.com.samuelmagro.reserva_salas_api.service;

import br.com.samuelmagro.reserva_salas_api.dto.usuario.UsuarioCreateDTO;
import br.com.samuelmagro.reserva_salas_api.dto.usuario.UsuarioFilterRequest;
import br.com.samuelmagro.reserva_salas_api.dto.usuario.UsuarioResponseDTO;
import br.com.samuelmagro.reserva_salas_api.dto.usuario.UsuarioUpdateDTO;
import br.com.samuelmagro.reserva_salas_api.exception.custom.RegistroDuplicadoCustomException;
import br.com.samuelmagro.reserva_salas_api.exception.custom.RegistroNaoEncontradoCustomException;
import br.com.samuelmagro.reserva_salas_api.mapper.UsuarioMapper;
import br.com.samuelmagro.reserva_salas_api.model.Usuario;
import br.com.samuelmagro.reserva_salas_api.repository.UsuarioRepository;
import br.com.samuelmagro.reserva_salas_api.specification.UsuarioSpecification;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public Page<UsuarioResponseDTO> findAll(Pageable pageable, @Valid UsuarioFilterRequest request) {
        Specification<Usuario> spec = Specification
                .where(UsuarioSpecification.nomeContem(request.nome()))
                .and(UsuarioSpecification.emailContem(request.email()))
                .and(UsuarioSpecification.ativo(request.ativo()))
                .and(UsuarioSpecification.criadoDepoisDe(request.criadoDepoisDe()))
                .and(UsuarioSpecification.criadoAte(request.criadoAte()));
        Page<Usuario> usuarioPage = usuarioRepository.findAll(spec, pageable);
        return usuarioPage.map(usuarioMapper::fromEntityToDTO);
    }

    public UsuarioResponseDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoCustomException(String.format("Usuário de id: %d não foi encontrado!", id)));
        return usuarioMapper.fromEntityToDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO create(@Valid UsuarioCreateDTO createDTO) {
        Usuario usuario = Usuario.builder()
                .nome(createDTO.nome())
                .email(createDTO.email())
                .ativo(true)
                .criadoEm(Instant.now())
                .build();

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RegistroDuplicadoCustomException("Email já cadastrado.");
        }

        return usuarioMapper.fromEntityToDTO(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioResponseDTO update(@Valid UsuarioUpdateDTO updateDTO, Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoCustomException(String.format("Usuário de id: %d não foi encontrado!", id)));

        if (usuarioRepository.existsByEmailAndIdNot(updateDTO.email(), id)) {
            throw new RegistroDuplicadoCustomException("Email já cadastrado.");
        }

        usuario.setNome(updateDTO.nome());
        usuario.setEmail(updateDTO.email());
        usuario.setAtivo(true);
        usuario.setAtualizadoEm(Instant.now());
        return usuarioMapper.fromEntityToDTO(usuarioRepository.save(usuario));
    }

    @Transactional
    public void delete(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoCustomException(String.format("Usuário de id: %d não foi encontrado!", id)));

        if (!usuario.getAtivo()) {
            return;
        } else {
            usuario.setAtivo(false);
            usuario.setAtualizadoEm(Instant.now());
            usuarioRepository.save(usuario);
        }
    }
}
