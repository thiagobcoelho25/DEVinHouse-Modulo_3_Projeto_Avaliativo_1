package com.example.devinhousemodulo_3_projeto_avaliativo_1.controllers;

import com.example.devinhousemodulo_3_projeto_avaliativo_1.enums.Classificacao;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.mappers.CheckinMapper;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.mappers.PassageiroMapper;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.mappers.PassageiroMapperImpl;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.CheckIn;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.Passageiro;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.dto.CheckInRequestDTO;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.dto.PassageiroCheckinResponseDTO;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.dto.PassageiroResponseDTO;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.services.CheckInService;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.services.PassageiroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class) // configurando classe de teste para rodar com o Spring (Spring Beans, http, etc...)
//@WebMvcTest(PassageiroController.class) // para termos acesso à objetos de chamadas http
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PassageiroControllerTest {

    @Autowired
    private MockMvc mockMvc;  // objeto para fazer requisicoes http

    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private PassageiroMapper passageiroMapper;
//    @MockBean  // mock para dependencias da classe de controller
//    private PassageiroService passageiroService;
//    @MockBean
//    private CheckInService checkInService;

    @Nested
    @DisplayName("Testes Para Passageiro")
    class passageirosTests {

        @Test
        @DisplayName("Deve Retornar Passageiro Correto Com Estato 200!")
        void getPassageiroByCpf() throws Exception {
            mockMvc.perform(get("/passageiros/{cpf}", "22222222222")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.cpf", is("222.222.222-22")));
        }

        @Test
        @DisplayName("Deve Retonar Erro De Passageiro Não Encontrado Com status 404!")
        void getPassageiroError() throws Exception {
            mockMvc.perform(get("/passageiros/{cpf}", "121819128")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound()) // 404
                    .andExpect(jsonPath("$.error", is(notNullValue())))
                    .andExpect(jsonPath("$.error", is("Falha De Busca de Entidade")))
                    .andExpect(jsonPath("$.message", is("Passageiro Não Existe")));
        }

    }

    @Nested
    @DisplayName("Testes Para Check-In")
    class checkInTests {

        @Test
        @DisplayName("Deve Retonar Erro De Passageiro Não Encontrado Com status 404!")
        void getCheckInPassageiroNotFound() throws Exception {
            String request = objectMapper.writeValueAsString(new CheckInRequestDTO("121819128", "1B", true));

            mockMvc.perform(post("/passageiros/confirmacao")
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound()) // 404
                    .andExpect(jsonPath("$.error", is(notNullValue())))
                    .andExpect(jsonPath("$.error", is("Falha De Busca de Entidade")))
                    .andExpect(jsonPath("$.message", is("Passageiro Não Encontrado!")));
        }


        @Test
        @DisplayName("Deve Retonar Erro De Assento Não Existente com status 400!")
        void getCheckInAssentoNotFound() throws Exception {
            String request = objectMapper.writeValueAsString(new CheckInRequestDTO("222.222.222-22", "9G", true));

            mockMvc.perform(post("/passageiros/confirmacao")
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest()) // 400
                    .andExpect(jsonPath("$.error", is(notNullValue())))
                    .andExpect(jsonPath("$.error", is("Regra de negócio nao respeitada")))
                    .andExpect(jsonPath("$.message", is("Assento Não Existe!")));
        }

        @Test
        @DisplayName("Deve Retonar Erro De Validação Caso CPF Nulo com status 400!")
        void getCheckInCPFNotSend() throws Exception {
            String request = objectMapper.writeValueAsString(new CheckInRequestDTO("", "9G", true));

            mockMvc.perform(post("/passageiros/confirmacao")
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest()) // 400
                    .andExpect(jsonPath("$.error", is(notNullValue())))
                    .andExpect(jsonPath("$.error", is("Erros de Validação")));
        }

        @Test
        @DisplayName("Deve Retonar Erro Caso Assento Esteja Reservado com status 409!")
        void getCheckInAssentoAlredyReserved() throws Exception {
            String request = objectMapper.writeValueAsString(new CheckInRequestDTO("222.222.222-22", "2B", true));

            mockMvc.perform(post("/passageiros/confirmacao")
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isConflict()) // 409
                    .andExpect(jsonPath("$.error", is(notNullValue())))
                    .andExpect(jsonPath("$.error", is("Regra de negócio nao respeitada")))
                    .andExpect(jsonPath("$.message", is("Assento Já Reservado!")));
        }


        @Test
        @DisplayName("Deve Retonar Erro Caso Idade Para Assento de Emergencia Seja Menor de 18 com status 400!")
        void getCheckInAssentoEmergenciaMenorIdade() throws Exception {
            String request = objectMapper.writeValueAsString(new CheckInRequestDTO("121.121.121-12", "5B", true));

            mockMvc.perform(post("/passageiros/confirmacao")
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest()) // 400
                    .andExpect(jsonPath("$.error", is(notNullValue())))
                    .andExpect(jsonPath("$.error", is("Regra de negócio nao respeitada")))
                    .andExpect(jsonPath("$.message", is("Assentos na fila de emergnecia não podem ter passageiros menores de 18")));
        }

        @Test
        @DisplayName("Deve Retonar Erro Caso Malas Não Depachadas Para Assento De Emergencia com status 400!")
        void getCheckInAssentoEmergenciaMalasNaoDespachadas() throws Exception {
            String request = objectMapper.writeValueAsString(new CheckInRequestDTO("222.222.222-22", "5B", false));

            mockMvc.perform(post("/passageiros/confirmacao")
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest()) // 400
                    .andExpect(jsonPath("$.error", is(notNullValue())))
                    .andExpect(jsonPath("$.error", is("Regra de negócio nao respeitada")))
                    .andExpect(jsonPath("$.message", is("Assentos na fila de emergnecia não podem ter passageiros sem malas despachadas")));
        }


        @Test
        @DisplayName("Deve Retonar Entidade CheckIn com status 200!")
        void createCheckin() throws Exception {
            String request = objectMapper.writeValueAsString(new CheckInRequestDTO("222.222.222-22", "5B", true));

            mockMvc.perform(post("/passageiros/confirmacao")
                            .content(request)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()) // 200
                    .andExpect(jsonPath("$.eticket", is(notNullValue())));

        }

    }
}