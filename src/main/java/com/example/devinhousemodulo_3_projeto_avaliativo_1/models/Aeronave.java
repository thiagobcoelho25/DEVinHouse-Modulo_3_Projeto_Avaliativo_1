package com.example.devinhousemodulo_3_projeto_avaliativo_1.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @ElementCollection
    private Set<String> poltronas;
//    @Transient
//    private Set<String> poltronas = Stream.of("A","B","C","D","E","F").collect(Collectors.toCollection(HashSet::new));
}
