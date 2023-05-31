package com.example.devinhousemodulo_3_projeto_avaliativo_1.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties({"message"})
public class ValidationErrorResponse extends StandardError {
    private List<Violation> campos = new ArrayList<>();

}

@Data
class Violation {

    private final String nome;

    private final String mensagem;

    public Violation(String nome, String mensagem) {
        this.nome = nome;
        this.mensagem = mensagem;
    }


}