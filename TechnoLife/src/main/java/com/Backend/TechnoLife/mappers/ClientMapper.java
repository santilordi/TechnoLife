package com.Backend.TechnoLife.mappers;

import com.Backend.TechnoLife.Dto.ClientDto;
import com.Backend.TechnoLife.Model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "lastName", target = "apellido")
    @Mapping(source = "rol", target = "rol")
    ClientDto toDto(Client client);

    @Mapping(source = "nombre", target = "name")
    @Mapping(source = "apellido", target = "lastName")
    Client toEntity(ClientDto clientDto);
}
