package br.com.samuelmagro.reserva_salas_api.controller;

import br.com.samuelmagro.reserva_salas_api.dto.usuario.UsuarioCreateDTO;
import br.com.samuelmagro.reserva_salas_api.dto.usuario.UsuarioFilterRequest;
import br.com.samuelmagro.reserva_salas_api.dto.usuario.UsuarioResponseDTO;
import br.com.samuelmagro.reserva_salas_api.dto.usuario.UsuarioUpdateDTO;
import br.com.samuelmagro.reserva_salas_api.service.UsuarioService;
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
@RequestMapping("/usuarios")
@Tag(name = "Recurso de usuarios.", description = "Endpoints para o gerenciamento do recurso usuarios.")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(
            summary = "Consulta de usuarios.",
            description = "Busca usuarios paginados com filtros."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = @Content)
    })
    @PageableAsQueryParam
    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDTO>> listarTodos(
            @ParameterObject
            @PageableDefault(
                    sort = "nome",
                    direction = Sort.Direction.ASC
            ) Pageable pageable, @Valid @ParameterObject UsuarioFilterRequest request) {

        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll(pageable, request));
    }

    @Operation(
            summary = "Busca de usuario.",
            description = "Busca usuario por Id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Objeto não encontrado.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findById(id));
    }

    @Operation(
            summary = "Cadastro de usuario.",
            description = "Cadastrar usuarios."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Objeto criado com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Existem campo(s) inválido(s) na requisição.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Vínculo já existente.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = @Content)
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioCreateDTO createDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.create(createDTO));
    }

    @Operation(
            summary = "Atualização de usuario.",
            description = "Altera dados do usuario por Id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Objeto atualizado com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Existem campo(s) inválido(s) na requisição.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Objeto não encontrado.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Vínculo já existente.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@Valid @RequestBody UsuarioUpdateDTO updateDTO,
                                       @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.update(updateDTO, id));
    }

    @Operation(
            summary = "Inativação de usuario.",
            description = "Inativa o status do usuario por Id."
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
        usuarioService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
