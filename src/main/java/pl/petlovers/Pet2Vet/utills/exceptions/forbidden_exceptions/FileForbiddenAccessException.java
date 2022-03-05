package pl.petlovers.Pet2Vet.utills.exceptions.forbidden_exceptions;

public class FileForbiddenAccessException extends ForbiddenAccessException {
  public FileForbiddenAccessException() {
    super("Nie masz dostępu do tej notatki.");
  }
}
