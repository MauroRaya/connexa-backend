package br.unisanta.connexa.controller;

import br.unisanta.connexa.model.Student;
import br.unisanta.connexa.repository.StudentRepository;
import br.unisanta.connexa.request.LoginRequest;
import br.unisanta.connexa.request.RegisterRequest;
import br.unisanta.connexa.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(
            StudentRepository studentRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil
    ) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(path = "register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        if (this.studentRepository.findByEmail(request.email()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("Este email já está cadastrado no sistema");
        }

        Student student = new Student();
        student.setName(request.name());
        student.setEmail(request.email());
        student.setPassword(passwordEncoder.encode(request.password()));

        this.studentRepository.save(student);

        return ResponseEntity.ok("Estudante cadastrado com sucesso");
    }

    @PostMapping(path = "login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        String token = this.jwtUtil.generateToken(request.email());

        return ResponseEntity.ok(token);
    }
}
