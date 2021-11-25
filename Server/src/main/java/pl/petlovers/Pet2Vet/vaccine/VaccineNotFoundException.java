package pl.petlovers.Pet2Vet.vaccine;

public class VaccineNotFoundException extends RuntimeException {
  protected VaccineNotFoundException(Long vaccineId) {
    super("Could not find vaccine with id: " + vaccineId);
  }
}
