package com.example.devinhousemodulo_3_projeto_avaliativo_1.controllers;

import com.example.devinhousemodulo_3_projeto_avaliativo_1.services.AeronaveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assentos")
public class AeronaveController {

    private final AeronaveService aeronaveService;

    public AeronaveController(AeronaveService aeronaveService) {
        this.aeronaveService = aeronaveService;
    }

    @GetMapping
    public ResponseEntity<String> getAllAssentos(){
        return ResponseEntity.ok(aeronaveService.getAllAssentos());
    }

}
