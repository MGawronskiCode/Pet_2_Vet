package pl.petlovers.Pet2Vet.specie;

public class SpecieNotFoundException extends RuntimeException {
  public SpecieNotFoundException(Long specieId) {
    super("Could not find specie with id: " + specieId);
  }
}
