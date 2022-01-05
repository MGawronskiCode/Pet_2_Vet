package pl.petlovers.Pet2Vet.exceptions.unautorized_exceptions;

public class MealUnauthorizedAttemptException extends UnauthorizedAttemptException {

  protected MealUnauthorizedAttemptException(String message) {
    super(message);
  }
}
