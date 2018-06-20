package tss.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author ljh
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Program not found")
public class ProgramNotFoundException extends RuntimeException {
}
