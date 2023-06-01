package com.example.devinhousemodulo_3_projeto_avaliativo_1.services;

import com.example.devinhousemodulo_3_projeto_avaliativo_1.enums.Classificacao;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.exceptions.BusinessBadRequestException;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.exceptions.BusinessRuleException;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.mappers.CheckinMapper;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.Aeronave;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.CheckIn;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.Passageiro;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.dto.CheckInRequestDTO;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.dto.CheckInResponseDTO;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.repositories.AeronaveRepository;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.repositories.CheckInRepository;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.repositories.PassageiroRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Service
public class CheckInService {

    private final CheckInRepository checkInRepository;
    private final PassageiroRepository passageiroRepository;
    private final AeronaveRepository aeronaveRepository;
    private final CheckinMapper mapper;

    public CheckInService(CheckInRepository checkInRepository, PassageiroRepository passageiroRepository, AeronaveRepository aeronaveRepository, CheckinMapper mapper) {
        this.checkInRepository = checkInRepository;
        this.passageiroRepository = passageiroRepository;
        this.aeronaveRepository = aeronaveRepository;
        this.mapper = mapper;
    }

    @Transactional
    public CheckInResponseDTO createChekIn(CheckInRequestDTO request){
        Passageiro passageiro = passageiroRepository.getPassageiroByCpf(request.getCpf()).orElseThrow(() -> new EntityNotFoundException("Passageiro Não Encontrado!"));
        Aeronave aeronave = aeronaveRepository.getReferenceById(1L);

        String[] assento = request.getAssento().trim().split("");
        if(Integer.parseInt(assento[0]) > aeronave.getQuantidade_fileras() || Integer.parseInt(assento[0]) < 0){
            throw new BusinessBadRequestException("Assento Não Existe!");
        } else if (!aeronave.getPoltronas().contains(assento[1])) {
            throw new BusinessBadRequestException("Assento Não Existe!");
        }

        if(checkInRepository.findCheckInByAssentoIgnoreCase(assento[1]).isPresent()){
            throw new BusinessRuleException("Assento Já Reservado!");
        }

        Period period = Period.between(passageiro.getData_nascimento(), LocalDate.now());

        if(aeronave.getFileiras_emergencia().contains(Integer.parseInt(assento[0])) && period.getYears() < 18){
            throw new BusinessBadRequestException("Assentos na fila de emergnecia não podem ter passageiros menores de 18");
        }

        if(aeronave.getFileiras_emergencia().contains(Integer.parseInt(assento[0])) && !request.getMalasDespachadas()){
            throw new BusinessBadRequestException("Assentos na fila de emergnecia não podem ter passageiros sem malas despachadas");
        }

        CheckIn checkIn = mapper.map(request);
        checkIn.setAeronave(aeronave);
        checkIn.setPassageiro(passageiro);
        checkIn.setE_ticket(UUID.randomUUID());

        CheckIn saved_checkIN = checkInRepository.save(checkIn);

        passageiro.setMilhas(passageiro.getMilhas() + Classificacao.getValorFromEnum(passageiro.getClassificacao()));
        passageiroRepository.save(passageiro);

        System.out.println("Confirmação feita pelo passageiro de CPF " + passageiro.getCpf() + " com e-ticket " + saved_checkIN.getE_ticket());

        return mapper.map(saved_checkIN);
    }
}
