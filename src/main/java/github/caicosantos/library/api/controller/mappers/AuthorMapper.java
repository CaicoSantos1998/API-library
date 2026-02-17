package github.caicosantos.library.api.controller.mappers;

import github.caicosantos.library.api.controller.dto.AuthorRegistrationDTO;
import github.caicosantos.library.api.controller.dto.AuthorResultSearchDTO;
import github.caicosantos.library.api.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toEntity(AuthorRegistrationDTO dto);

    AuthorResultSearchDTO toDTO(Author author);
}
