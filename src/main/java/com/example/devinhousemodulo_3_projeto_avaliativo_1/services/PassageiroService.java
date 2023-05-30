package com.example.devinhousemodulo_3_projeto_avaliativo_1.services;

import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.Passageiro;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.repositories.PassageiroRepository;
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

}
