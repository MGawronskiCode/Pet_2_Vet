package pl.petlovers.Pet2Vet.utills.exceptions.forbidden_exceptions;

public class NoteForbiddenAccessException extends ForbiddenAccessException {

  public NoteForbiddenAccessException() {
    super("Nie masz dostępu do tej notatki.");
  }

}
