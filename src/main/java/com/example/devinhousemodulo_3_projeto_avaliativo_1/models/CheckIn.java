package com.example.devinhousemodulo_3_projeto_avaliativo_1.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID e_ticket;
    private String assento;
    private boolean malas_despachadas;
    @Column(updatable=false)
    @CreationTimestamp
    private LocalDateTime data_hora_confirmacao;
    @OneToOne
    @JoinColumn(name = "passageiro_id")
    private Passageiro passageiro;
    @ManyToOne
    @JoinColumn(name = "aeronave_id")
    private Aeronave aeronave;
}
