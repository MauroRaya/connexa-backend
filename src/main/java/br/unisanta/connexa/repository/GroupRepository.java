package br.unisanta.connexa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unisanta.connexa.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {}