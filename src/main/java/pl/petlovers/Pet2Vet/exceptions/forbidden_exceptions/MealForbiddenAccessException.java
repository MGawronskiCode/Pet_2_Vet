package pl.petlovers.Pet2Vet.exceptions.forbidden_exceptions;

public class MealForbiddenAccessException extends ForbiddenAccessException {
  protected MealForbiddenAccessException(String message) {
    super(message);
  }
}
