package br.unisanta.connexa.dto;

import java.util.Set;
import java.util.stream.Collectors;

import br.unisanta.connexa.model.Group;
import br.unisanta.connexa.model.Modality;

public record GroupDTO(
    Long id,
    String name,
    String subject,
    Modality modality,
    String location,
    String objective,
    Set<StudentDTO> students
) {
    public GroupDTO(Group group) {
        this(
            group.getId(),
            group.getName(),
            group.getSubject(),
            group.getModality(),
            group.getLocation(),
            group.getObjective(),
            group.getStudents().stream()
                .map(StudentDTO::new)
                .collect(Collectors.toSet())
        );
    }
}