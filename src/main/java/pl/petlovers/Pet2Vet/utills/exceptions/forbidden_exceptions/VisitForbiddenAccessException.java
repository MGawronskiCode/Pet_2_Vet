package pl.petlovers.Pet2Vet.utills.exceptions.forbidden_exceptions;

public class VisitForbiddenAccessException extends ForbiddenAccessException {

  public VisitForbiddenAccessException() {
    super("Nie masz dostępu do tej wizyty.");
  }

}
