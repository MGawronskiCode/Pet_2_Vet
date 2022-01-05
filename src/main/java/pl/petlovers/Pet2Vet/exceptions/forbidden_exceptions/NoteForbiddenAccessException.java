package pl.petlovers.Pet2Vet.exceptions.forbidden_exceptions;

public class NoteForbiddenAccessException extends ForbiddenAccessException {
  protected NoteForbiddenAccessException(String message) {
    super(message);
  }
}
