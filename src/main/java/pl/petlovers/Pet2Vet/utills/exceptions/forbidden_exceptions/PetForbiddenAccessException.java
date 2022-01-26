package pl.petlovers.Pet2Vet.utills.exceptions.forbidden_exceptions;

public class PetForbiddenAccessException extends ForbiddenAccessException {
  public PetForbiddenAccessException(String message) {
    super(message);
  }
}
