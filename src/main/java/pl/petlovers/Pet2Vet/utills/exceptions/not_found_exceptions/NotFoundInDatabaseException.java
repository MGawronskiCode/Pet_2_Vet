package pl.petlovers.Pet2Vet.utills.exceptions.not_found_exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Slf4j
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public abstract class NotFoundInDatabaseException extends RuntimeException {

  protected NotFoundInDatabaseException(String message) {
    super(message);
    log.error("---Error: " + message);
  }
}
