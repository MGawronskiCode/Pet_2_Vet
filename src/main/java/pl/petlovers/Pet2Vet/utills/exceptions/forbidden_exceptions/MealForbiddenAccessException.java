package pl.petlovers.Pet2Vet.utills.exceptions.forbidden_exceptions;

public class MealForbiddenAccessException extends ForbiddenAccessException {

  public MealForbiddenAccessException() {
    super("Nie masz dostępu do tego posiłku.");
  }

}
