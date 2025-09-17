package br.unisanta.connexa.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Email é obrigatório")
        @Size(max = 100, message = "O email deve ter no máximo 100 caracteres")
        @Email(message = "Formato de email inválido")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@universidade\\.edu\\.br$", message = "O email deve terminar com @universidade.edu.br")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
        @Size(max = 100, message = "A senha deve ter no máximo 100 caracteres")
        @Pattern(regexp = ".*[a-z].*", message = "A senha deve conter pelo menos 1 letra minúscula")
        @Pattern(regexp = ".*[A-Z].*", message = "A senha deve conter pelo menos 1 letra maiúscula")
        @Pattern(regexp = ".*\\d.*", message = "A senha deve conter pelo menos 1 número")
        String password
) {}
