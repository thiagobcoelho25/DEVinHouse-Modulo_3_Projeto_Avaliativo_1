package com.example.devinhousemodulo_3_projeto_avaliativo_1.models.dto;

import com.example.devinhousemodulo_3_projeto_avaliativo_1.enums.Classificacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassageiroResponseDTO {
    private String cpf;
    private String nome;
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate dataNascimento;
    private Classificacao classificacao;
    private Integer milhas;

}
