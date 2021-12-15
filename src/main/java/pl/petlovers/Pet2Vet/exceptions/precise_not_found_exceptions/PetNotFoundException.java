package pl.petlovers.Pet2Vet.exceptions.precise_not_found_exceptions;

import pl.petlovers.Pet2Vet.exceptions.NotFoundInDatabaseException;

public class PetNotFoundException extends NotFoundInDatabaseException {
  public PetNotFoundException(long petId) {
    super("Could not find pet with id: " + petId);
  }
}
