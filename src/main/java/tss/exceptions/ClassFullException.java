package tss.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author ljh
 */
@ResponseStatus(value = HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, reason = "Class is full")
public class ClassFullException extends RuntimeException {
}
