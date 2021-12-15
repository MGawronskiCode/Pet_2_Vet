package pl.petlovers.Pet2Vet.exceptions.unautorized_exceptions;

public class NoteUnauthorizedAttemptException extends UnauthorizedAttemptException {
  protected NoteUnauthorizedAttemptException(String message) {
    super(message);
  }
}
