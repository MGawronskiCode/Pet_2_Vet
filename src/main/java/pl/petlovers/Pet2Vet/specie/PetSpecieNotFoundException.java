package pl.petlovers.Pet2Vet.specie;

public class PetSpecieNotFoundException extends RuntimeException {
  public PetSpecieNotFoundException(Long specieId) {
    super("Could not find specie with id: " + specieId);
  }
}
