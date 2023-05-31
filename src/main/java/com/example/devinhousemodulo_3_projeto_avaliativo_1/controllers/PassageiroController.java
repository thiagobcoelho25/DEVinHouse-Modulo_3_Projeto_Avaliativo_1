package com.example.devinhousemodulo_3_projeto_avaliativo_1.controllers;

import com.example.devinhousemodulo_3_projeto_avaliativo_1.mappers.PassageiroMapper;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.dto.PassageiroCheckinResponseDTO;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.dto.PassageiroResponseDTO;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.services.PassageiroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/passageiros")
public class PassageiroController {

    private final PassageiroService passageiroService;
    private final PassageiroMapper mapper;

    public PassageiroController(PassageiroService passageiroService, PassageiroMapper mapper) {
        this.passageiroService = passageiroService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<PassageiroCheckinResponseDTO>> getPassageiros(){
        return ResponseEntity.ok(mapper.map_with_checkin(passageiroService.getAllPassageiros()));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<PassageiroResponseDTO> getPassageiroByCpf(@PathVariable String cpf){
        return ResponseEntity.ok(mapper.map(passageiroService.getPassageiroByCpf(cpf)));
    }
}
