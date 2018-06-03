package tss.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author reeve
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Current year and semester of arrangement not found")
public class CurrentYearSemesterOfArrangementNotFoundException extends RuntimeException {
}
