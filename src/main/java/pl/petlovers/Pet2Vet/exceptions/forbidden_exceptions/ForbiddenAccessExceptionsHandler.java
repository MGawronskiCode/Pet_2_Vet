package pl.petlovers.Pet2Vet.exceptions.forbidden_exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ForbiddenAccessExceptionsHandler {

  @ResponseBody
  @ExceptionHandler(AppUserForbiddenAccessException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  String appUserForbiddenAccessException(AppUserForbiddenAccessException exception) {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(MealForbiddenAccessException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  String mealForbiddenAccessException(MealForbiddenAccessException exception) {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(NoteForbiddenAccessException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  String noteForbiddenAccessException(NoteForbiddenAccessException exception) {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(PetForbiddenAccessException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  String petForbiddenAccessException(PetForbiddenAccessException exception) {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(SpecieForbiddenAccessException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  String specieForbiddenAccessException(SpecieForbiddenAccessException exception) {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(VaccineForbiddenAccessException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  String vaccineForbiddenAccessException(VaccineForbiddenAccessException exception) {
    return exception.getMessage();
  }

}
