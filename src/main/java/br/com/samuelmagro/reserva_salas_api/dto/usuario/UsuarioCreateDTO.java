package br.com.samuelmagro.reserva_salas_api.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioCreateDTO(

        @Schema(description = "Nome do usuario")
        @NotBlank(message = "O campo nome não pode ser nulo.")
        @Size(min = 5, max = 100, message = "O campo nome deve ter entre {min} e {max} caracteres.")
        String nome,

        @Schema(description = "Email do usuario")
        @Email(message = "E-mail com formato inválido.")
        @NotBlank(message = "O campo email não pode ser nulo.")
        @Size(min = 5, max = 255, message = "O campo email deve ter entre {min} e {max} caracteres.")
        String email
) {
}
