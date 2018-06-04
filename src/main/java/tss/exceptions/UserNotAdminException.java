package tss.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author ljh
 */

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You are not an admin")
public class UserNotAdminException extends RuntimeException {
}
