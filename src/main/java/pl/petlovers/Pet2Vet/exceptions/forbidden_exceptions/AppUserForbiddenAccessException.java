package pl.petlovers.Pet2Vet.exceptions.forbidden_exceptions;

public class AppUserForbiddenAccessException extends ForbiddenAccessException{
  protected AppUserForbiddenAccessException(String message) {
    super(message);
  }
}
