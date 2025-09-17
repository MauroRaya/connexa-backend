package br.unisanta.connexa.service;

import br.unisanta.connexa.model.Student;
import br.unisanta.connexa.repository.StudentRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final StudentRepository studentRepository;

    public UserDetailsServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = this.studentRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Estudante não encontrado"));

        return User.builder()
                .username(student.getEmail())
                .password(student.getPassword())
                .roles("USER")
                .build();
    }
}
