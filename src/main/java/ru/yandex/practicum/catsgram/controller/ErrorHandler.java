package ru.yandex.practicum.catsgram.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.catsgram.exception.*;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public ErrorResponse handleHappinessOverflow(final NotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse handleHappinessOverflow(final DuplicatedDataException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse handleHappinessOverflow(final ConditionsNotMetException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse handleHappinessOverflow(final ParameterNotValidException e) {
        return new ErrorResponse(String.format("Некорректное значение параметра %s: %s",
                e.getParameter(), e.getReason()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleHappinessOverflow(final Throwable e) {
        return new ErrorResponse("Произошла непредвиденная ошибка.");
    }
}