package tss.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "Class has finished, you cannot drop it")
public class ClassFinishedNotForDroppingException extends RuntimeException {
}
