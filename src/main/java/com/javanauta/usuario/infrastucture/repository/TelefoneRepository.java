package com.javanauta.usuario.infrastucture.repository;

import com.javanauta.usuario.infrastucture.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
