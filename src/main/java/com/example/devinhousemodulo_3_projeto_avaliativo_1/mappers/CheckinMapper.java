package com.example.devinhousemodulo_3_projeto_avaliativo_1.mappers;

import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.CheckIn;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.dto.CheckInRequestDTO;
import com.example.devinhousemodulo_3_projeto_avaliativo_1.models.dto.CheckInResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CheckinMapper {

    @Mapping(target= "malas_despachadas", source = "malasDespachadas")
    CheckIn map(CheckInRequestDTO source);

    @Mapping(target = "dataHoraConfirmacao", source = "data_hora_confirmacao")
    @Mapping(target = "eticket", source = "e_ticket")
    CheckInResponseDTO map(CheckIn source);
}
