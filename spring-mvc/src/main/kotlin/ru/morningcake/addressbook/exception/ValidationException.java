package ru.morningcake.addressbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Request")
public class ValidationException extends IllegalArgumentException {

    public ValidationException(String msg) {
        super(msg);
    }
}
