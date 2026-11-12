package br.com.samuelmagro.reserva_salas_api.controller;

import br.com.samuelmagro.reserva_salas_api.dto.reserva.*;
import br.com.samuelmagro.reserva_salas_api.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @Operation(
            summary = "Consulta de reservas.",
            description = "Busca reservas paginadas com filtros."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReservaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @PageableAsQueryParam
    @GetMapping
    public ResponseEntity<Page<ReservaResponseDTO>> listarTodas(
            @ParameterObject
            @PageableDefault(
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable, @Valid @ParameterObject ReservaFilterRequest request
            ) {
        return ResponseEntity.status(HttpStatus.OK).body(reservaService.findAll(pageable, request));
    }

    @Operation(
            summary = "Busca de reserva.",
            description = "Busca reserva pelo id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReservaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Objeto não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> buscarId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(reservaService.findById(id));
    }
    @Operation(
            summary = "Cadastro de reserva.",
            description = "Cadastrar reservas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Objeto criada com sucesso.",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReservaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Existem campo(s) inválido(s) na requisição."),
            @ApiResponse(responseCode = "409", description = "Vínculo já existente.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> criar(@Valid @RequestBody ReservaCreateDTO createDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.create(createDTO));
    }

    @Operation(
            summary = "Atualização de reserva.",
            description = "Altera dados de reserva por Id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Objeto atualizado com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Existem campo(s) inválido(s) na requisição.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Objeto não encontrado.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflito existente.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> atualizar(@Valid @RequestBody ReservaUpdateDTO updateDTO,
                                                        @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(reservaService.update(updateDTO, id));
    }

    @Operation(
            summary = "Cancelamento de reserva.",
            description = "Cancela reserva por Id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Objeto atualizado com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "Objeto não encontrado.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = @Content)
    })
    @DeleteMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        reservaService.cancelar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Reativação de reserva.",
            description = "Reativa reserva por Id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Objeto atualizado com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Transição rejeitada.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Objeto não encontrado.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflito existente.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = @Content)
    })
    @PatchMapping("/{id}/reativar")
    public ResponseEntity<ReservaResponseDTO> reativar(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(reservaService.reativar(id));
    }
}
