package pl.petlovers.Pet2Vet.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.petlovers.Pet2Vet.exceptions.precise_not_found_exceptions.*;

@Slf4j
@ControllerAdvice
public class NotFoundInDatabaseExceptionsHandler {

  @ResponseBody
  @ExceptionHandler(AppUserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String appUserNotFoundHandler(AppUserNotFoundException exception) {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(MealNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String mealNotFoundHandler(MealNotFoundException exception) {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(NoteNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String noteNotFoundHandler(NoteNotFoundException exception) {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(PetNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String petNotFoundHandler(PetNotFoundException exception) {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(PetSpecieNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String petSpecieNotFoundHandler(PetSpecieNotFoundException exception) {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(VaccineNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String vaccineNotFoundHandler(VaccineNotFoundException exception) {
    return exception.getMessage();
  }

}
