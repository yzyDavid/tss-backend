package tss.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author reeve
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Class not found")
public class ClazzNotFoundException extends RuntimeException {
}
