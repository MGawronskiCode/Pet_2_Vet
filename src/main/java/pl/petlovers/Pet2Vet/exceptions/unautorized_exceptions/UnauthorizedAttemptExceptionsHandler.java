package pl.petlovers.Pet2Vet.exceptions.unautorized_exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class UnauthorizedAttemptExceptionsHandler {

  @ResponseBody
  @ExceptionHandler(AppUserUnauthorizedAttemptException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  String appUserUnauthorizedAttemptException(AppUserUnauthorizedAttemptException exception) {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(MealUnauthorizedAttemptException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  String mealUnauthorizedAttemptException(MealUnauthorizedAttemptException exception) {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(NoteUnauthorizedAttemptException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  String noteUnauthorizedAttemptException(NoteUnauthorizedAttemptException exception) {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(PetUnauthorizedAttemptException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  String petUnauthorizedAttemptException(PetUnauthorizedAttemptException exception) {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(SpecieUnauthorizedAttemptException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  String specieUnauthorizedAttemptException(SpecieUnauthorizedAttemptException exception) {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(VaccineUnauthorizedAttemptException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  String vaccineUnauthorizedAttemptException(VaccineUnauthorizedAttemptException exception) {
    return exception.getMessage();
  }

}
