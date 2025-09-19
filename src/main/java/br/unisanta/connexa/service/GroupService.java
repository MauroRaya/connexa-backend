package br.unisanta.connexa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.unisanta.connexa.model.Group;
import br.unisanta.connexa.model.Student;
import br.unisanta.connexa.repository.GroupRepository;
import br.unisanta.connexa.repository.StudentRepository;
import br.unisanta.connexa.request.CreateGroupRequest;
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

    public List<Group> findAll() {
        return this.groupRepository.findAll();
    }

    public Optional<Group> findById(Long id) {
        return this.groupRepository.findById(id);
    }

    public Group save(CreateGroupRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        System.out.println(email);

        Student creator = this.studentRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Estudante não encontrado"));

        Group group = new Group();
        group.setName(request.name());
        group.setSubject(request.subject());
        group.setModality(request.modality());
        group.setLocation(request.location());
        group.setObjective(request.objective());

        group.getStudents().add(creator);
        creator.getGroups().add(group);

        return this.groupRepository.save(group);
    }

    public void join(long groupId) throws EntityNotFoundException, IllegalArgumentException {
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
    }
}
