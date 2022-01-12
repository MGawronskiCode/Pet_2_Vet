package pl.petlovers.Pet2Vet.exceptions.forbidden_exceptions;

public class VisitForbiddenAccessException extends ForbiddenAccessException {

  public VisitForbiddenAccessException() {
    super("Nie masz dostępu do tej wizyty.");
  }

  public VisitForbiddenAccessException(String message) {
    super(message);
  }

}
