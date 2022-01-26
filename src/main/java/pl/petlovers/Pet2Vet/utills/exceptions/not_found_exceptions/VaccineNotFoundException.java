package pl.petlovers.Pet2Vet.utills.exceptions.not_found_exceptions;

public class VaccineNotFoundException extends NotFoundInDatabaseException {
  public VaccineNotFoundException(Long vaccineId) {
    super("Could not find vaccine with id: " + vaccineId);
  }
}
