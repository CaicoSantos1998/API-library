package github.caicosantos.library.api.controller.mappers;

import github.caicosantos.library.api.controller.dto.AuthorDTO;
import github.caicosantos.library.api.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toEntity(AuthorDTO dto);

    AuthorDTO toDTO(Author author);
}
