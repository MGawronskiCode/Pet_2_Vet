package pl.petlovers.Pet2Vet.appUser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class AppUserNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(AppUserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String appUserNotFoundHandler(AppUserNotFoundException exception) {
    log.error("Error: ", exception);
    return exception.getMessage();
  }

}
