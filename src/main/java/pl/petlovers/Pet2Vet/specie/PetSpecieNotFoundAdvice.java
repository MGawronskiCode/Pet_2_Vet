package pl.petlovers.Pet2Vet.specie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class PetSpecieNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(PetSpecieNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String kindNotFoundHandler(PetSpecieNotFoundException exception) {
    log.error("Error: ", exception);
    return exception.getMessage();
  }

}
