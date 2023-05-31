package com.example.devinhousemodulo_3_projeto_avaliativo_1.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Aeronave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipo;
    private int quantidade_fileras;

    @Transient
    private List<Integer> fileiras_emergencia = Arrays.asList(5,6);

    @Transient
    private List<String> poltronas = Arrays.asList("A","B","C","D","E","F");
}
