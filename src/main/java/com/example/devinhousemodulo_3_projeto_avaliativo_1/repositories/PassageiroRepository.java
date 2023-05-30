package com.example.devinhousemodulo_3_projeto_avaliativo_1.repositories;

import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.Passageiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PassageiroRepository extends JpaRepository<Passageiro, UUID> {

    Optional<Passageiro> getPassageiroByCpf(String cpf);
}
