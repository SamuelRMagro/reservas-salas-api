package br.com.samuelmagro.reserva_salas_api.mapper;

import br.com.samuelmagro.reserva_salas_api.dto.usuario.UsuarioResponseDTO;
import br.com.samuelmagro.reserva_salas_api.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioResponseDTO fromEntityToDTO(Usuario usuario);
}
