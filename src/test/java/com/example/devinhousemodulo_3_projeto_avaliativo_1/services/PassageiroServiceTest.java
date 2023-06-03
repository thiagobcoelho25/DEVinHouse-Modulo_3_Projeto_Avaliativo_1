package com.example.devinhousemodulo_3_projeto_avaliativo_1.services;

import com.example.devinhousemodulo_3_projeto_avaliativo_1.enums.Classificacao;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.Passageiro;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.repositories.PassageiroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PassageiroServiceTest {

    @Mock
    PassageiroRepository passageiroRepository;

    @InjectMocks
    PassageiroService passageiroService;

    @Test
    @DisplayName("Recuperar Todos os Passageiros Cadastrados!")
    void getAllPassageiros() {
        List<Passageiro> passageiros_mock = List.of(
                new Passageiro(1L,"2222222222", "Thiago", LocalDate.now(), Classificacao.VIP, 100, null),
                new Passageiro(2L,"3333333333", "Guto", LocalDate.now(), Classificacao.OURO, 80, null)
                );
        Mockito.when(passageiroRepository.findAll()).thenReturn(passageiros_mock);
        List<Passageiro> passageiros_service_returned = passageiroService.getAllPassageiros();

        assertFalse(passageiros_service_returned.isEmpty());
        assertEquals(passageiros_service_returned.size(), passageiros_mock.size());
    }

    @Test
    @DisplayName("Recuperar Passageiro Correto Pelo CPF!")
    void getPassageiroByCpf() {
        Passageiro passageiro_mock = new Passageiro(1L,"222.222.222-22", "Thiago", LocalDate.now(), Classificacao.VIP, 100, null);
        Mockito.when(passageiroRepository.getPassageiroByCpf("222.222.222-22")).thenReturn(Optional.of(passageiro_mock));
        Passageiro passageiro_serivce_returned = passageiroService.getPassageiroByCpf("222.222.222-22");

        // Verificar se o passageiro foi encontrado
        assertNotNull(passageiro_serivce_returned);
        assertEquals("222.222.222-22", passageiro_serivce_returned.getCpf());
    }

    @Test
    @DisplayName("Deve Lancar Exceção De Entidade Não Encontrada!")
    void cpfNaoExiste(){
        Mockito.when(passageiroRepository.getPassageiroByCpf(Mockito.anyString())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> passageiroService.getPassageiroByCpf("222.222.222-22"));
    }

    @Test
    @DisplayName("Regex Deve Ser Chamado Corretamente!")
    void regexCpf(){
        Passageiro passageiro_mock = new Passageiro(1L,"222.222.222-22", "Thiago", LocalDate.now(), Classificacao.VIP, 100, null);
        Mockito.when(passageiroRepository.getPassageiroByCpf("222.222.222-22")).thenReturn(Optional.of(passageiro_mock));
        passageiroService.getPassageiroByCpf("22222222222");

        // Verificar se o método do repository foi chamado com o CPF correto
        Mockito.verify(passageiroRepository, Mockito.times(1)).getPassageiroByCpf("222.222.222-22");
    }
}