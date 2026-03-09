package github.caicosantos.library.api.controller;

import github.caicosantos.library.api.controller.dto.AuthorRegistrationDTO;
import github.caicosantos.library.api.controller.dto.AuthorResultSearchDTO;
import github.caicosantos.library.api.controller.mappers.AuthorMapper;
import github.caicosantos.library.api.model.Author;
import github.caicosantos.library.api.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@Tag(name = "Authors")
public class AuthorController implements GenericController {

    private final AuthorService service;
    private final AuthorMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Save", description = "Register the new author!")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "The author was successfully registered!"),
            @ApiResponse(responseCode = "409", description = "The author is already registered in the database!"),
            @ApiResponse(responseCode = "422", description = "Validation error!")

    })
    public ResponseEntity<Void> save(@RequestBody @Valid AuthorRegistrationDTO dto) {
        Author author = mapper.toEntity(dto);
        service.save(author);
        return ResponseEntity
                .created(generateHeaderLocation(author.getId()))
                .build();
    }

    @Operation(summary = "Get", description = "Find the author by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The author was found!"),
            @ApiResponse(responseCode = "404", description = "The author could not be found!")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResultSearchDTO> getById(@PathVariable UUID id) {
        return service
                .getById(id)
                .map(author -> {
                    AuthorResultSearchDTO dto = mapper.toDTO(author);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Delete", description = "Delete the author")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deleting with success!"),
            @ApiResponse(responseCode = "400", description = "The author has books registered in the database!"),
            @ApiResponse(responseCode = "404", description = "The author could not be found!"),
    })
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        return service.getById(id)
                .map(author -> {
                    service.deleteById(author);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Search", description = "Perform author searches using parameters!")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success!")
    })
    public ResponseEntity<List<AuthorResultSearchDTO>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String nationality) {
        List<Author> resultSearch = service.search(name, nationality);
        List<AuthorResultSearchDTO> authorList =
                resultSearch
                        .stream()
                        .map(mapper::toDTO)
                        .toList();
        return ResponseEntity.ok(authorList);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Update", description = "Update the exist author!")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Updated with success!"),
            @ApiResponse(responseCode = "404", description = "The author could not be found!"),
            @ApiResponse(responseCode = "409)", description = "The author is already registered in the database!")
    })
    public ResponseEntity<Void> update(
            @RequestBody @Valid AuthorRegistrationDTO dto,
            @PathVariable UUID id) {
        Optional<Author> obj = service.getById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var author = obj.get();
        author.setName(dto.name());
        author.setNationality(dto.nationality());
        author.setBirthDate(dto.birthDate());
        service.update(author);
        return ResponseEntity.noContent().build();
    }
}
