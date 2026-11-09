package br.com.samuelmagro.reserva_salas_api.mapper;

import br.com.samuelmagro.reserva_salas_api.dto.sala.SalaResponseDTO;
import br.com.samuelmagro.reserva_salas_api.model.Sala;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SalaMapper {
    SalaResponseDTO fromEntityToDTO(Sala sala);
}
