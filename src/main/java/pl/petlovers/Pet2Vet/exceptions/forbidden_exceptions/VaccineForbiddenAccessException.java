package pl.petlovers.Pet2Vet.exceptions.forbidden_exceptions;

public class VaccineForbiddenAccessException extends ForbiddenAccessException {

  public VaccineForbiddenAccessException() {
    super("Nie masz dostępu do tego szczepienia.");
  }

  public VaccineForbiddenAccessException(String message) {
    super(message);
  }
}
