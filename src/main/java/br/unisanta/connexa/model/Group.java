package br.unisanta.connexa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "groups")
@Getter
@Setter
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String subject;

    @Enumerated(EnumType.STRING)
    private Modality modality;

    private String location;
    private String objective;

    @ManyToMany(mappedBy = "groups")
    @JsonBackReference
    private Set<Student> students = new HashSet<>();
}
