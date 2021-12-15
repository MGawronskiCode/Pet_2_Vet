package pl.petlovers.Pet2Vet.exceptions.unautorized_exceptions;

public class PetUnauthorizedAttemptException extends UnauthorizedAttemptException {
  protected PetUnauthorizedAttemptException(String message) {
    super(message);
  }
}
