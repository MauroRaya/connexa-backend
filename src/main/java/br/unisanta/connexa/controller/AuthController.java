package br.unisanta.connexa.controller;

import br.unisanta.connexa.dto.StudentDTO;
import br.unisanta.connexa.model.Student;
import br.unisanta.connexa.request.LoginRequest;
import br.unisanta.connexa.request.RegisterRequest;
import br.unisanta.connexa.service.AuthService;
import jakarta.validation.Valid;

import java.util.HashSet;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
        AuthService authService,
        PasswordEncoder passwordEncoder
    ) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "register")
    public ResponseEntity<StudentDTO> register(@Valid @RequestBody RegisterRequest request) {
        Student student = new Student();
        student.setName(request.name());
        student.setEmail(request.email());
        student.setPassword(passwordEncoder.encode(request.password()));
        student.setGroups(new HashSet<>());

        StudentDTO createdStudentDTO = this.authService.save(student);

        return ResponseEntity
            .ok()
            .body(createdStudentDTO);
    }

    @PostMapping(path = "login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        Student student = new Student();
        student.setEmail(request.email());
        student.setPassword(request.password());

        String token = authService.login(student);

        return ResponseEntity.ok(token);
    }
}
