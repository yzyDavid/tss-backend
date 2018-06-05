package tss.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "It is not in register time!")
public class SelectionTimeInvalidRegisterException extends RuntimeException {
}
