package pl.petlovers.Pet2Vet.utills.exceptions.forbidden_exceptions;

public class AppUserWithThisLoginAlreadyExistException extends ForbiddenAccessException {

  public AppUserWithThisLoginAlreadyExistException() {
    super("Użytkownik o takim loginie już istnieje. Wybierz inny login użytkownika.");
  }
}
