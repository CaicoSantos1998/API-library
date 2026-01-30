package github.caicosantos.library.api.controller.common;

import github.caicosantos.library.api.controller.dto.ErrorField;
import github.caicosantos.library.api.controller.dto.ErrorResponse;
import github.caicosantos.library.api.exceptions.SearchCombinationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErrorField> errorList = fieldErrors
                .stream()
                .map(fe -> new ErrorField(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Error validation!", errorList);
    }

    @ExceptionHandler(SearchCombinationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerSearchCombinationNotFoundException(SearchCombinationNotFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), "There is no author with that combination!", Collections.emptyList());
    }
}
