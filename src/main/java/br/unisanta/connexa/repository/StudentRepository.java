package br.unisanta.connexa.repository;

import br.unisanta.connexa.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    public Student findByEmail(String email);
}
