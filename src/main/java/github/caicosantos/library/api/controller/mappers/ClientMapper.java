package github.caicosantos.library.api.controller.mappers;

import github.caicosantos.library.api.controller.dto.ClientRegistrationDTO;
import github.caicosantos.library.api.controller.dto.ClientResultSearchDTO;
import github.caicosantos.library.api.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientRegistrationDTO dto);
    ClientResultSearchDTO toDTO(Client client);
}
