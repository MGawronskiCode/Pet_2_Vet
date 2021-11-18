package pl.petlovers.Pet2Vet.pet;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.petlovers.Pet2Vet.vaccine.VaccineNotFoundException;

@ControllerAdvice
public class PetNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(VaccineNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String petNotFoundHandler(PetNotFoundException exception) {
    return exception.getMessage();
  }

}
