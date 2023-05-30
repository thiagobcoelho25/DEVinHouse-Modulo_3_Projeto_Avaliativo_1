package com.example.devinhousemodulo_3_projeto_avaliativo_1.mappers;

import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.Passageiro;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.dto.PassageiroResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PassageiroMapper {

    @Mapping(target = "dataNascimento", source = "data_nascimento")
    @Mapping(target = "eticket", source = "checkIn.e_ticket")
    @Mapping(target = "dataHoraConfirmacao", source = "checkIn.data_hora_confirmacao")
    PassageiroResponseDTO map(Passageiro source);


    List<PassageiroResponseDTO> map(List<Passageiro> source);
}
