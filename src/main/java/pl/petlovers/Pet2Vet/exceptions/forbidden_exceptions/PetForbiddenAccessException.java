package pl.petlovers.Pet2Vet.exceptions.forbidden_exceptions;

public class PetForbiddenAccessException extends ForbiddenAccessException {
  protected PetForbiddenAccessException(String message) {
    super(message);
  }
}
