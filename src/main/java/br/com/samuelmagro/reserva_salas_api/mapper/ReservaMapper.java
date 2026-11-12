package br.com.samuelmagro.reserva_salas_api.mapper;

import br.com.samuelmagro.reserva_salas_api.dto.reserva.ReservaResponseDTO;
import br.com.samuelmagro.reserva_salas_api.model.Reserva;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    ReservaResponseDTO fromEntityToDTO(Reserva reserva);
}
