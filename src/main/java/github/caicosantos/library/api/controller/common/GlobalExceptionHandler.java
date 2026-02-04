package github.caicosantos.library.api.controller.common;

import github.caicosantos.library.api.controller.dto.ErrorField;
import github.caicosantos.library.api.controller.dto.ErrorResponse;
import github.caicosantos.library.api.exceptions.DuplicateRegisterException;
import github.caicosantos.library.api.exceptions.InvalidRecordException;
import github.caicosantos.library.api.exceptions.OperationNotPermittedException;
import github.caicosantos.library.api.exceptions.SearchCombinationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), Collections.emptyList());
    }

    @ExceptionHandler(DuplicateRegisterException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerDuplicateRegisterException(DuplicateRegisterException e) {
        return ErrorResponse.conflict(e.getMessage());

    }

    @ExceptionHandler(OperationNotPermittedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerOperationNotPermittedException(OperationNotPermittedException e) {
        return ErrorResponse.responseStandard(e.getMessage());
    }

    @ExceptionHandler(InvalidRecordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerInvalidRecordException(InvalidRecordException e) {
        return ErrorResponse.responseStandard(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerErrorsNoHandling(RuntimeException e) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected internal error occurred!", List.of());
    }
}
