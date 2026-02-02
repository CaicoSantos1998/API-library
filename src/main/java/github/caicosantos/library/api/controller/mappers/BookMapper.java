package github.caicosantos.library.api.controller.mappers;

import github.caicosantos.library.api.controller.dto.BookRegistrationDTO;
import github.caicosantos.library.api.controller.dto.BookResultSearchDTO;
import github.caicosantos.library.api.model.Book;
import github.caicosantos.library.api.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public abstract class BookMapper {
    @Autowired
    AuthorRepository authorRepository;

    @Mapping(target = "author", expression = "java( authorRepository.findById(dto.idAuthor()).orElse(null) )")
    public abstract Book toEntity(BookRegistrationDTO dto);

    public abstract BookResultSearchDTO toDTO(Book book);

}
