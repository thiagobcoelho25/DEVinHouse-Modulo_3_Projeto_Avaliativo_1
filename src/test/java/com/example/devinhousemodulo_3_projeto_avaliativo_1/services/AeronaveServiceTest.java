package com.example.devinhousemodulo_3_projeto_avaliativo_1.services;

import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.Aeronave;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.repositories.AeronaveRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class AeronaveServiceTest {

    @Mock
    AeronaveRepository aeronaveRepository;

    @InjectMocks
    AeronaveService aeronaveService;

    @Test
    @DisplayName("Deve Retornar Todos os Asssentos!")
    void getAllAssentos(){
        Aeronave aero_1 = new Aeronave(1L,"Aero 1", 2,
                new ArrayList<Integer>(List.of(2)), new ArrayList<>(List.of("A","B","C","D","E","F")));
        Mockito.when(aeronaveRepository.findById(1L)).thenReturn(Optional.of(aero_1));
        String assentos_mocked = "1A, \n1B, \n1C, \n1D, \n1E, \n1F, \n2A, \n2B, \n2C, \n2D, \n2E, \n2F, \n";
        String assentos_returned_service = aeronaveService.getAllAssentos();

        assertEquals(assentos_returned_service, assentos_mocked);
    }

    @Test
    @DisplayName("Deve Lancar Exceção De Entidade Não Encontrada!")
    void aeroNaoExiste(){
        Mockito.when(aeronaveRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> aeronaveService.getAllAssentos());
    }

}