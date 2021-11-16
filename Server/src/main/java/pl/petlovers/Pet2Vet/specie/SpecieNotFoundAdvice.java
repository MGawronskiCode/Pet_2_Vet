package pl.petlovers.Pet2Vet.specie;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SpecieNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(SpecieNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String kindNotFoundHandler(SpecieNotFoundException exception) {
    return exception.getMessage();
  }

}
