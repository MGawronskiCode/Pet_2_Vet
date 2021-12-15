package pl.petlovers.Pet2Vet.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class OtherExceptionsHandler {


  static final String ERROR = "---Error: ";

  @ResponseBody
  @ExceptionHandler(NullPointerException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String nullPointerHandler(NullPointerException exception) {

    log.error(ERROR + exception.toString());
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String illegalArgumentHandler(IllegalArgumentException exception) {
    log.error(ERROR + exception.toString());
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(IllegalStateException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String illegalStateHandler(IllegalStateException exception) {
    log.error(ERROR + exception.toString());
    return exception.getMessage();
  }

}
