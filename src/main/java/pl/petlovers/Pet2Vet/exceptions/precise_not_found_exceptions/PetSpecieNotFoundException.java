package pl.petlovers.Pet2Vet.exceptions.precise_not_found_exceptions;

import pl.petlovers.Pet2Vet.exceptions.NotFoundInDatabaseException;

public class PetSpecieNotFoundException extends NotFoundInDatabaseException {
  public PetSpecieNotFoundException(Long specieId) {
    super("Could not find specie with id: " + specieId);
  }
}
