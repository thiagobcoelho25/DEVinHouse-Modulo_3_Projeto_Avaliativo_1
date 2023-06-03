package com.example.devinhousemodulo_3_projeto_avaliativo_1.services;

import com.example.devinhousemodulo_3_projeto_avaliativo_1.enums.Classificacao;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.exceptions.BusinessBadRequestException;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.exceptions.BusinessRuleException;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.mappers.CheckinMapper;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.Aeronave;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.CheckIn;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.Passageiro;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.dto.CheckInRequestDTO;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.repositories.AeronaveRepository;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.repositories.CheckInRepository;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.repositories.PassageiroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CheckInServiceTest {

    @Mock
    CheckInRepository checkInRepository;
    @Mock
    PassageiroRepository passageiroRepository;
    @Mock
    AeronaveRepository aeronaveRepository;
    @Spy
    CheckinMapper mapper = Mappers.getMapper(CheckinMapper.class);
    @InjectMocks
    CheckInService checkInService;

    @BeforeEach
    public void setup(){
        this.passageiro = new Passageiro(1L,"222.222.222-22", "Thiago", LocalDate.now(), Classificacao.VIP, 100, null);
        this.aeronave = new Aeronave(1L,"Aero 1", 2, new ArrayList<Integer>(List.of(2)), new ArrayList<>(List.of("A","B","C","D","E","F")));
        this.request = new CheckInRequestDTO("222.222.222-22","1B",true);
        this.checkIn = new CheckIn(1L, UUID.randomUUID(), "1B", true, LocalDateTime.now(), this.passageiro, this.aeronave);
    }

    Passageiro passageiro;
    Aeronave aeronave;
    CheckIn checkIn;
    CheckInRequestDTO request;

    @Test
    @DisplayName("Deve Lancar Exceção Se Passageiro Não Existir!")
    void sePassageiroNaoExiste(){
        Mockito.when(passageiroRepository.getPassageiroByCpf(Mockito.anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> checkInService.createChekIn(request));
    }

    @Test
    @DisplayName("Deve Lancar Exceção Se Fileira Não Existir!")
    void seFileiraNaoExiste(){
        this.request.setAssento("9B");
        Mockito.when(passageiroRepository.getPassageiroByCpf(Mockito.anyString())).thenReturn(Optional.of(passageiro));
        Mockito.when(aeronaveRepository.getReferenceById(Mockito.anyLong())).thenReturn(aeronave);

        assertThrows(BusinessBadRequestException.class, () -> checkInService.createChekIn(request));
    }

    @Test
    @DisplayName("Deve Lancar Exceção Se Poltrona Não Existir!")
    void sePoltronaNaoExiste(){
        this.request.setAssento("1G");
        Mockito.when(passageiroRepository.getPassageiroByCpf(Mockito.anyString())).thenReturn(Optional.of(passageiro));
        Mockito.when(aeronaveRepository.getReferenceById(Mockito.anyLong())).thenReturn(aeronave);

        assertThrows(BusinessBadRequestException.class, () -> checkInService.createChekIn(request));
    }

    @Test
    @DisplayName("Deve Lancar Exceção Se Assento Já Estiver Reservado!")
    void seAssentoReservado(){
        Mockito.when(passageiroRepository.getPassageiroByCpf(Mockito.anyString())).thenReturn(Optional.of(passageiro));
        Mockito.when(aeronaveRepository.getReferenceById(Mockito.anyLong())).thenReturn(aeronave);
        Mockito.when(checkInRepository.findCheckInByAssentoIgnoreCase(Mockito.anyString())).thenReturn(Optional.of(new CheckIn()));

//        RETORNA ERROR DE "PotentialStubbingProblem"
//        Mockito.when(checkInRepository.findCheckInByAssentoIgnoreCase("hdjahjdsahjd")).thenReturn(Optional.of(new CheckIn()));

        assertThrows(BusinessRuleException.class, () -> checkInService.createChekIn(request));
    }

    @Test
    @DisplayName("Deve Lancar Exceção Se Idad   e Para Assento Não Permitida!")
    void seIdadeMenorParaFileira(){
        request.setAssento("2B");
        Mockito.when(passageiroRepository.getPassageiroByCpf(Mockito.anyString())).thenReturn(Optional.of(passageiro));
        Mockito.when(aeronaveRepository.getReferenceById(Mockito.anyLong())).thenReturn(aeronave);


        assertThrows(BusinessBadRequestException.class, () -> checkInService.createChekIn(request));
    }

    @Test
    @DisplayName("Deve Lancar Exceção Se Mala Não Despachada Para Assento De Emergencia Não Permitida!")
    void seMalasNaoDespachadasParaFileiraEmergencia(){
        request.setAssento("2B");
        request.setMalasDespachadas(false);
        Mockito.when(passageiroRepository.getPassageiroByCpf(Mockito.anyString())).thenReturn(Optional.of(passageiro));
        Mockito.when(aeronaveRepository.getReferenceById(Mockito.anyLong())).thenReturn(aeronave);


        assertThrows(BusinessBadRequestException.class, () -> checkInService.createChekIn(request));
    }

    @Test
    @DisplayName("Deve Calcular Corretamente Quantidade de Milhas Adquirida!")
    void seCalculoMilhasCorreto(){
        Mockito.when(passageiroRepository.getPassageiroByCpf(Mockito.anyString())).thenReturn(Optional.of(passageiro));
        Mockito.when(aeronaveRepository.getReferenceById(Mockito.anyLong())).thenReturn(aeronave);
        Mockito.when(checkInRepository.save(Mockito.any())).thenReturn(checkIn);
        checkInService.createChekIn(request);

        assertEquals(200, passageiro.getMilhas());
    }

    @Test
    @DisplayName("Deve Criar Check-In Quando Dados Estiverem Correto!")
    void seDadosCorretosEntaoCriaEntidadeCheckIn(){
        Mockito.when(passageiroRepository.getPassageiroByCpf(Mockito.anyString())).thenReturn(Optional.of(passageiro));
        Mockito.when(aeronaveRepository.getReferenceById(Mockito.anyLong())).thenReturn(aeronave);
        Mockito.when(checkInRepository.save(Mockito.any())).thenReturn(checkIn);

        assertEquals(checkIn.getE_ticket(), checkInService.createChekIn(request).getEticket());
    }

}