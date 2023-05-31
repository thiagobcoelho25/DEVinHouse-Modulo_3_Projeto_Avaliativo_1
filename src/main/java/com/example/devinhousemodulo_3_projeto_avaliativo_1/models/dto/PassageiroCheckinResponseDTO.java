package com.example.devinhousemodulo_3_projeto_avaliativo_1.models.dto;

import com.example.devinhousemodulo_3_projeto_avaliativo_1.enums.Classificacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassageiroCheckinResponseDTO {

    private String cpf;
    private String nome;

    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate dataNascimento;
    private Classificacao classificacao;
    private Integer milhas;
    private UUID eticket;
    private String assento;
    private LocalDateTime dataHoraConfirmacao;
}
