package br.unisanta.connexa.dto;

import br.unisanta.connexa.model.Student;

public record StudentDTO(
    Long id,
    String name,
    String email
) {
    public StudentDTO(Student student) {
        this(
            student.getId(),
            student.getName(),
            student.getEmail()
        );
    }
}