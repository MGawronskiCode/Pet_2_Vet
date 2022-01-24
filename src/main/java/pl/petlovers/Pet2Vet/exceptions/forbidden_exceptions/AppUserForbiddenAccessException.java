package pl.petlovers.Pet2Vet.exceptions.forbidden_exceptions;

public class AppUserForbiddenAccessException extends ForbiddenAccessException {

  public AppUserForbiddenAccessException() {
    super("Nie masz dostępu do tego użytkownika.");
  }

}
