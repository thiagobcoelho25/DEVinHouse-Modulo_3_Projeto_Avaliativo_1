package com.example.devinhousemodulo_3_projeto_avaliativo_1.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckInResponseDTO {

    private UUID eticket;
    private LocalDateTime dataHoraConfirmacao;


}
