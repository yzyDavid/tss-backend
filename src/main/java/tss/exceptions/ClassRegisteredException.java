package tss.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author ljh
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Class is registered already")
public class ClassRegisteredException extends RuntimeException {
}
