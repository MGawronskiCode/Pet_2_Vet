package pl.petlovers.Pet2Vet.exceptions.forbidden_exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public abstract class ForbiddenAccessException extends RuntimeException{
  protected ForbiddenAccessException(String message) {
    super(message);
    log.error("---Error: " + message);
  }
}
