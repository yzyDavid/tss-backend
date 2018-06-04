package tss.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author ljh
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Course name and teacher name are null!")
public class ClassSearchInvalidException extends RuntimeException {
}
