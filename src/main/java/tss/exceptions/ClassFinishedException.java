package tss.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author ljh
 */

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Class has finished")
public class ClassFinishedException extends RuntimeException {
}
