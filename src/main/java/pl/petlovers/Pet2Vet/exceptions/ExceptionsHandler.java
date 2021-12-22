package pl.petlovers.Pet2Vet.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.petlovers.Pet2Vet.exceptions.forbidden_exceptions.ForbiddenAccessException;
import pl.petlovers.Pet2Vet.exceptions.not_found_exceptions.*;
import pl.petlovers.Pet2Vet.exceptions.unautorized_exceptions.UnauthorizedAttemptException;

@Slf4j
@ControllerAdvice
public class ExceptionsHandler {

  static final String ERROR = "---Error: ";

  @ResponseBody
  @ExceptionHandler(ForbiddenAccessException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  String forbiddenAccessException(ForbiddenAccessException exception) {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(NotFoundInDatabaseException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String notFoundException(NotFoundInDatabaseException exception) {
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(UnauthorizedAttemptException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  String unauthorizedAttemptException(UnauthorizedAttemptException exception) {
    return exception.getMessage();
  }

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

  @ResponseBody
  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  String httpMessageNotReadableException(HttpMessageNotReadableException exception) {
    log.error(ERROR + exception.toString());
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(NumberFormatException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String numberFormatException(NumberFormatException exception) {
    log.error(ERROR + exception.toString());
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String dtaIntegrityViolationException(DataIntegrityViolationException exception) {
    log.error(ERROR + exception.toString());
    return "Could not execute statement, remember to send the data required in this query. If you are trying to delete something, make sure it is not included in some objects";
  }

  @ResponseBody
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String constraintViolationException(ConstraintViolationException exception) {
    log.error(ERROR + exception.toString());
    return exception.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(InvalidDataAccessApiUsageException.class)
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  String invalidDataAccessApiUsageException(InvalidDataAccessApiUsageException exception) {
    log.error(ERROR + exception.toString());
    return exception.toString();
  }


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

  @ResponseBody
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  String otherException(Exception exception) {
    log.error(ERROR + exception.toString());
    return exception.toString();
  }

}
