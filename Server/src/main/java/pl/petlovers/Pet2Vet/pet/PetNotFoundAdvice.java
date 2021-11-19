package pl.petlovers.Pet2Vet.pet;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PetNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(PetNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String petNotFoundHandler(PetNotFoundException exception) {
    return exception.getMessage();
  }

}
