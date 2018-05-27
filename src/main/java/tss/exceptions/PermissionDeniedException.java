package tss.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author reeve
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Permission denied")
public class PermissionDeniedException extends RuntimeException {
}
