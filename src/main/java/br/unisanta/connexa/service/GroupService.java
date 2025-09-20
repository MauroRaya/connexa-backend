package br.unisanta.connexa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.unisanta.connexa.dto.GroupDTO;
import br.unisanta.connexa.model.Group;
import br.unisanta.connexa.model.Student;
import br.unisanta.connexa.repository.GroupRepository;
import br.unisanta.connexa.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    public GroupService(
        GroupRepository groupRepository,
        StudentRepository studentRepository
    ) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
    }

    public List<GroupDTO> findAll() {
        return this.groupRepository.findAll().stream()
            .map(GroupDTO::new)
            .toList();
    }

    public Optional<GroupDTO> findById(Long id) {
        return this.groupRepository.findById(id)
            .map(GroupDTO::new);
    }

    public GroupDTO save(Group group) throws EntityNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Student creator = this.studentRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Estudante não encontrado"));

        group.getStudents().add(creator);
        creator.getGroups().add(group);

        Group createdGroup = this.groupRepository.save(group);

        return new GroupDTO(createdGroup);
    }

    public GroupDTO join(Long groupId) throws EntityNotFoundException, IllegalArgumentException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Student student = this.studentRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Estudante não encontrado"));

        Group group = this.groupRepository.findById(groupId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Grupo com id %d não encontrado", groupId))
        );

        if (group.getStudents().contains(student)) {
            throw new IllegalArgumentException("Estudante já faz parte desse grupo");
        }

        group.getStudents().add(student);
        student.getGroups().add(group);

        this.groupRepository.save(group);
        this.studentRepository.save(student);

        return new GroupDTO(group);
    }
}
