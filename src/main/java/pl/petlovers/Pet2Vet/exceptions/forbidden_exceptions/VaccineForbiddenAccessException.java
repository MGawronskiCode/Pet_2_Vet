package pl.petlovers.Pet2Vet.exceptions.forbidden_exceptions;

public class VaccineForbiddenAccessException extends ForbiddenAccessException{
  protected VaccineForbiddenAccessException(String message) {
    super(message);
  }
}
