package br.unisanta.connexa.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import br.unisanta.connexa.model.Student;
import br.unisanta.connexa.repository.StudentRepository;
import br.unisanta.connexa.utils.JwtUtil;

@Service
public class AuthService {
    private final StudentRepository studentRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(
            StudentRepository studentRepository,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil
    ) {
        this.studentRepository = studentRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public Student save(Student student) throws IllegalArgumentException {
        if (this.studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Este email já está cadastrado no sistema");
        }

        return this.studentRepository.save(student);
    }

    public String login(Student student) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(student.getEmail(), student.getPassword())
        );

        return jwtUtil.generateToken(student.getEmail());
    }
}
