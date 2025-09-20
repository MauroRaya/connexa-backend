package br.unisanta.connexa.dto;

import java.util.Set;

import br.unisanta.connexa.model.Group;

public record StudentDTO(
    Long id,
    String name,
    String email,
    Set<Group> groups
) {}