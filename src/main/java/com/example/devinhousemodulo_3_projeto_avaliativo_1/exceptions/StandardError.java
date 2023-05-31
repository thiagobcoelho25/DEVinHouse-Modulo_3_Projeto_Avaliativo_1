package com.example.devinhousemodulo_3_projeto_avaliativo_1.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardError {
    private Long timestamp;
    private Integer status;
    private String path;
    private String error;
    private String message;

}
