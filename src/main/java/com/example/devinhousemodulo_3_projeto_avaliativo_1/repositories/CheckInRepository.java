package com.example.devinhousemodulo_3_projeto_avaliativo_1.repositories;

import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

    Optional<CheckIn> findCheckInByAssentoIgnoreCase(String assento);

}
