package br.com.samuelmagro.reserva_salas_api.controller;

import br.com.samuelmagro.reserva_salas_api.dto.sala.SalaCreateDTO;
import br.com.samuelmagro.reserva_salas_api.dto.sala.SalaFilterRequest;
import br.com.samuelmagro.reserva_salas_api.dto.sala.SalaResponseDTO;
import br.com.samuelmagro.reserva_salas_api.dto.sala.SalaUpdateDTO;
import br.com.samuelmagro.reserva_salas_api.service.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/salas")
@Tag(name = "Recurso de salas.", description = "Endpoints para o gerenciamento do recurso salas.")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @Operation(
            summary = "Consulta de salas.",
            description = "Busca salas paginadas com filtros."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SalaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = @Content)
    })
    @PageableAsQueryParam
    @GetMapping
    public ResponseEntity<Page<SalaResponseDTO>> listarTodos(
            @ParameterObject
            @PageableDefault(
                    sort = "nome",
                    direction = Sort.Direction.ASC
            ) Pageable pageable, @Valid @ParameterObject SalaFilterRequest request
            ) {
        return ResponseEntity.status(HttpStatus.OK).body(salaService.findAll(pageable, request));
    }

    @Operation(
            summary = "Consulta de sala.",
            description = "Busca sala por Id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SalaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Objeto não encontrado.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<SalaResponseDTO> buscarId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(salaService.findById(id));
    }

    @Operation(
            summary = "Cadastro de sala.",
            description = "Cadastra sala."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sala cadastrada com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SalaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Existem campo(s) inválido(s) na requisição.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Vínculo já existente.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = @Content)
    })
    @PostMapping
    public ResponseEntity<SalaResponseDTO> cadastrar(@Valid @RequestBody SalaCreateDTO createDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(salaService.create(createDTO));
    }

    @Operation(
            summary = "Atualização de sala.",
            description = "Atualiza sala por Id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala atualizada com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SalaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Existem campo(s) inválido(s) na requisição.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Objeto não encontrado.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Vínculo já existente.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<SalaResponseDTO> atualizar(@Valid @RequestBody SalaUpdateDTO updateDTO,
                                       @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(salaService.update(updateDTO, id));

    }

    @Operation(
            summary = "Inativação de sala.",
            description = "Inativa sala por Id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Objeto atualizado com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "Objeto não encontrado.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = @Content)
    })
    @DeleteMapping("/{id}/inativar")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        salaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
