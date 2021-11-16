package pl.petlovers.Pet2Vet.vaccine;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class VaccineNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(VaccineNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String vaccineNotFoundHandler(VaccineNotFoundException exception) {
    return exception.getMessage();
  }

}
