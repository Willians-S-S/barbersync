package br.com.wss.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class ExceptionsHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public BusinessExceptionDto handle(BusinessException exception, HttpServletRequest request) {
        return new BusinessExceptionDto(exception.getStatusCode().value(), exception.getReason(), request.getRequestURI());
    }

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public BusinessExceptionDto handle(BadCredentialsException exception, HttpServletRequest request) {
        return new BusinessExceptionDto(401, exception.getMessage(), request.getRequestURI());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateTimeParseException.class)
    public BusinessExceptionDto handle(DateTimeParseException exception, HttpServletRequest request) {
        return new BusinessExceptionDto(400, exception.getMessage(), request.getRequestURI());
    }

}
