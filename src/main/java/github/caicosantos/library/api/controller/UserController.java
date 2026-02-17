package github.caicosantos.library.api.controller;

import github.caicosantos.library.api.controller.dto.UserDTO;
import github.caicosantos.library.api.controller.mappers.UserMapper;
import github.caicosantos.library.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements GenericController{
    private final UserService service;
    private final UserMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> save(@RequestBody UserDTO dto) {
        var user = mapper.toEntity(dto);
        service.save(user);
        return ResponseEntity
                .created(generateHeaderLocation(user.getId()))
                .build();
    }
}
