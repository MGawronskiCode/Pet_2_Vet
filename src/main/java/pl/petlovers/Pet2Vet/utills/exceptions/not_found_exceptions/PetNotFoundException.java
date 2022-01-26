package pl.petlovers.Pet2Vet.utills.exceptions.not_found_exceptions;

public class PetNotFoundException extends NotFoundInDatabaseException {
  public PetNotFoundException(long petId) {
    super("Could not find pet with id: " + petId);
  }
}
