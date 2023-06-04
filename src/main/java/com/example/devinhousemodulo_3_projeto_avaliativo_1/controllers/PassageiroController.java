package com.example.devinhousemodulo_3_projeto_avaliativo_1.controllers;

import com.example.devinhousemodulo_3_projeto_avaliativo_1.mappers.PassageiroMapper;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.dto.CheckInRequestDTO;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.dto.CheckInResponseDTO;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.dto.PassageiroCheckinResponseDTO;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.dto.PassageiroResponseDTO;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.services.CheckInService;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.services.PassageiroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passageiros")
@Tag(name = "passageiros")
public class PassageiroController {

    private final PassageiroService passageiroService;
    private final PassageiroMapper mapper;
    private final CheckInService checkInService;

    public PassageiroController(PassageiroService passageiroService, PassageiroMapper mapper, CheckInService checkInService) {
        this.passageiroService = passageiroService;
        this.mapper = mapper;
        this.checkInService = checkInService;
    }

    @Operation(summary = "Realiza requisição de Passageiros", method = "GET")
    @GetMapping
    public ResponseEntity<List<PassageiroCheckinResponseDTO>> getPassageiros(){
        return ResponseEntity.ok(mapper.map_with_checkin(passageiroService.getAllPassageiros()));
    }

    @Operation(summary = "Realiza requisição de Passageiro Por CPF", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Passageiro Não Existe")
    })
    @GetMapping("/{cpf}")
    public ResponseEntity<PassageiroResponseDTO> getPassageiroByCpf(@PathVariable String cpf){
        return ResponseEntity.ok(mapper.map(passageiroService.getPassageiroByCpf(cpf)));
    }

    @PostMapping("/confirmacao")
    public ResponseEntity<CheckInResponseDTO> createCheckin(@RequestBody @Valid CheckInRequestDTO request) {
        return ResponseEntity.ok(checkInService.createChekIn(request));
    }
}
