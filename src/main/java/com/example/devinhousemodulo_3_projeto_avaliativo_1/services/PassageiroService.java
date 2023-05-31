package com.example.devinhousemodulo_3_projeto_avaliativo_1.services;

import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.Passageiro;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.repositories.PassageiroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassageiroService {

    private final PassageiroRepository passageiroRepository;

    public PassageiroService(PassageiroRepository passageiroRepository) {
        this.passageiroRepository = passageiroRepository;
    }

    public List<Passageiro> getAllPassageiros(){
        return passageiroRepository.findAll();
    }

    public Passageiro getPassageiroByCpf(String cpf){
        String cpf_format = cpf.replaceAll("[^\\d]", "").replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        return passageiroRepository.getPassageiroByCpf(cpf_format).orElseThrow(() -> new EntityNotFoundException("Passageiro Não Existe"));
    }

}
