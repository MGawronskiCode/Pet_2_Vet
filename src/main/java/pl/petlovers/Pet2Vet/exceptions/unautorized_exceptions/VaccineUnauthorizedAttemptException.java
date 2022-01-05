package pl.petlovers.Pet2Vet.exceptions.unautorized_exceptions;

public class VaccineUnauthorizedAttemptException extends UnauthorizedAttemptException {
  protected VaccineUnauthorizedAttemptException(String message) {
    super(message);
  }
}
