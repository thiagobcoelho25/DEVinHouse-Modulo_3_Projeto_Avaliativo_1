package com.example.devinhousemodulo_3_projeto_avaliativo_1.services;

import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.Aeronave;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.repositories.AeronaveRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AeronaveService {

    private final AeronaveRepository aeronaveRepository;

    public AeronaveService(AeronaveRepository aeronaveRepository) {
        this.aeronaveRepository = aeronaveRepository;
    }

    public String getAllAssentos(){
        Aeronave aero_1 = aeronaveRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Aeronave não existe"));
        StringBuilder str_b = new StringBuilder();
        for (int i = 0; i < aero_1.getQuantidade_fileras(); i++) {
            for (int j = 0; j < aero_1.getPoltronas().size(); j++){
                str_b.append(i + 1).append(aero_1.getPoltronas().get(j)).append(", \n");
            }
        }
        return str_b.toString();
    }
}
