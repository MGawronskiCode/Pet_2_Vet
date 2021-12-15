package pl.petlovers.Pet2Vet.exceptions.unautorized_exceptions;

public class AppUserUnauthorizedAttemptException extends UnauthorizedAttemptException {

  protected AppUserUnauthorizedAttemptException(String message) {
    super(message);
  }
}
