package pl.petlovers.Pet2Vet.exceptions.unautorized_exceptions;

public class SpecieUnauthorizedAttemptException extends UnauthorizedAttemptException {
  protected SpecieUnauthorizedAttemptException(String message) {
    super(message);
  }
}
