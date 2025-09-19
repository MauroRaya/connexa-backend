package br.unisanta.connexa.request;

import br.unisanta.connexa.model.Modality;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;

public record CreateGroupRequest(
    @NotBlank(message = "Nome é obrigatório")
    String name,

    @NotBlank(message = "Matéria é obrigatória")
    String subject,

    @Enumerated(EnumType.STRING)
    Modality modality,

    @NotBlank(message = "Local é obrigatório")
    String location,

    @NotBlank(message = "Objetivo é obrigatório")
    String objective
) {}