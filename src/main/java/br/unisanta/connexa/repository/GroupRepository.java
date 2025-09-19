package br.unisanta.connexa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unisanta.connexa.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {}