package pl.petlovers.Pet2Vet.appUser;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.petlovers.Pet2Vet.pet.PetNotFoundException;

@ControllerAdvice
public class AppUserNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(AppUserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String appUserNotFoundHandler(AppUserNotFoundException exception) {
    return exception.getMessage();
  }

}