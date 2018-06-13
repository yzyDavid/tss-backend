package tss.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "It is not in drop time!")
public class SelectionTimeInvalidDropException extends RuntimeException {
}
