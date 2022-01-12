package pl.petlovers.Pet2Vet.exceptions.forbidden_exceptions;

public class MealForbiddenAccessException extends ForbiddenAccessException {

  public MealForbiddenAccessException() {
    super("Nie masz dostępu do tego posiłku.");
  }

  public MealForbiddenAccessException(String message) {
    super(message);
  }
}
