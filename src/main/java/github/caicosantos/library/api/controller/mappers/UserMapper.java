package github.caicosantos.library.api.controller.mappers;

import github.caicosantos.library.api.controller.dto.UserDTO;
import github.caicosantos.library.api.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO dto);
}
