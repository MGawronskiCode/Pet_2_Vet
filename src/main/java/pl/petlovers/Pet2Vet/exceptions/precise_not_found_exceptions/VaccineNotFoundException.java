package pl.petlovers.Pet2Vet.exceptions.precise_not_found_exceptions;

import pl.petlovers.Pet2Vet.exceptions.NotFoundInDatabaseException;

public class VaccineNotFoundException extends NotFoundInDatabaseException {
  public VaccineNotFoundException(Long vaccineId) {
    super("Could not find vaccine with id: " + vaccineId);
  }
}
