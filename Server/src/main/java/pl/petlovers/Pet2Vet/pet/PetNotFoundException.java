package pl.petlovers.Pet2Vet.pet;

public class PetNotFoundException extends RuntimeException {
  public PetNotFoundException(long petId) {
    super("Could not find pet with id: " + petId);
  }
}
