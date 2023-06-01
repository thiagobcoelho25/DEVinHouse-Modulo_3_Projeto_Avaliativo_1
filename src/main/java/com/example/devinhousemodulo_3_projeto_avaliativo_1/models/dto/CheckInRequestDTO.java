package com.example.devinhousemodulo_3_projeto_avaliativo_1.models.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckInRequestDTO {

    @NotEmpty(message = "CPF Não Pode Ser Vazio")
    private String cpf;

    @NotEmpty(message = "Assento Não Pode Ser Vazio")
    @Length(max = 2, min = 2, message = "Assento Deve Ser No Minimo e Maximo de 2 Caracteres")
    private String assento;

    @NotNull(message = "Não Pode Ser Nulo")
    private Boolean malasDespachadas;


}
