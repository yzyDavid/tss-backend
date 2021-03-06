package tss.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Jinhong Li
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User not a student")
public class UserNotStudentException extends RuntimeException {
}
