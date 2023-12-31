package org.example.sbdcoursework.controller.advice;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.example.sbdcoursework.dto.ApiErrorDTO;
import org.example.sbdcoursework.exception.InvalidArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class InputValidationControllerAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<List<ApiErrorDTO>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception
    ) {
        log.error(exception.getMessage(), exception);

        List<ApiErrorDTO> errorInfos = exception.getFieldErrors().stream()
                .map(error -> new ApiErrorDTO(
                        InvalidArgumentException.errorCode(),
                        error.getField() + ": " + error.getDefaultMessage()
                ))
                .toList();

        return new ResponseEntity<>(errorInfos, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ApiErrorDTO> handleConstraintViolationException(
            ConstraintViolationException exception
    ) {
        log.error(exception.getMessage(), exception);

        ApiErrorDTO errorInfo = ApiErrorDTO.builder()
                .code(InvalidArgumentException.errorCode())
                .description(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
