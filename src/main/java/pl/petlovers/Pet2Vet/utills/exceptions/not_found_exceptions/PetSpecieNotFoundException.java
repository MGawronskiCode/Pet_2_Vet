package pl.petlovers.Pet2Vet.utills.exceptions.not_found_exceptions;

public class PetSpecieNotFoundException extends NotFoundInDatabaseException {
  public PetSpecieNotFoundException(Long specieId) {
    super("Could not find specie with id: " + specieId);
  }
}
