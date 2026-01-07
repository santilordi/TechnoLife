package com.Backend.TechnoLife.mappers;

import com.Backend.TechnoLife.Dto.ClientDto;
import com.Backend.TechnoLife.Model.Client;
import com.Backend.TechnoLife.Model.ClientStatus;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-07T10:14:21-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public ClientDto toDto(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientDto.ClientDtoBuilder clientDto = ClientDto.builder();

        clientDto.nombre( client.getName() );
        clientDto.apellido( client.getLastName() );
        if ( client.getRol() != null ) {
            clientDto.rol( client.getRol().name() );
        }
        clientDto.id( client.getId() );
        clientDto.email( client.getEmail() );

        return clientDto.build();
    }

    @Override
    public Client toEntity(ClientDto clientDto) {
        if ( clientDto == null ) {
            return null;
        }

        Client.ClientBuilder client = Client.builder();

        client.name( clientDto.getNombre() );
        client.lastName( clientDto.getApellido() );
        client.id( clientDto.getId() );
        client.email( clientDto.getEmail() );
        if ( clientDto.getRol() != null ) {
            client.rol( Enum.valueOf( ClientStatus.class, clientDto.getRol() ) );
        }

        return client.build();
    }
}
