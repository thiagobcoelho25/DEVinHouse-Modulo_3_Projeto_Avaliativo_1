package com.example.devinhousemodulo_3_projeto_avaliativo_1.enums;

import java.util.HashMap;
import java.util.Map;

public enum Classificacao {
    VIP(100),
    OURO(80),
    PRATA(50),
    BRONZE(30),
    ASSOCIADO(10);

    private static final Map<Classificacao, Integer> POR_CLASSIFICACAO = new HashMap<>();

    static {
        for (Classificacao e: values()) {
            POR_CLASSIFICACAO.put(e, e.label);
        }
    }

    public final Integer label;

    Classificacao(Integer label){
        this.label = label;
    }

    public static Integer getValorFromEnum(Classificacao tipo) {
        return POR_CLASSIFICACAO.get(tipo);
    }
}
