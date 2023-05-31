package com.example.devinhousemodulo_3_projeto_avaliativo_1.repositories;

import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.Aeronave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AeronaveRepository extends JpaRepository<Aeronave, Long> {
}
