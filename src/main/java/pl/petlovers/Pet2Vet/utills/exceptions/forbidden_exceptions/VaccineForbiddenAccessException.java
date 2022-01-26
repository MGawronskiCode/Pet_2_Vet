package pl.petlovers.Pet2Vet.utills.exceptions.forbidden_exceptions;

public class VaccineForbiddenAccessException extends ForbiddenAccessException {

  public VaccineForbiddenAccessException() {
    super("Nie masz dostępu do tego szczepienia.");
  }

}
