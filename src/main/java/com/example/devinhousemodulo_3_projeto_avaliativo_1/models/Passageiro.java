package com.example.devinhousemodulo_3_projeto_avaliativo_1.models;

import com.example.devinhousemodulo_3_projeto_avaliativo_1.enums.Classificacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Passageiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String cpf;
    private String nome;
    private LocalDate data_nascimento;
    @Enumerated(EnumType.STRING)
    private Classificacao classificacao;
    private Integer milhas;
    @OneToOne(mappedBy = "passageiro")
    private CheckIn checkIn;
}
