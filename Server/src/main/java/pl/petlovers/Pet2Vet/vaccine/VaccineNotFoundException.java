package pl.petlovers.Pet2Vet.vaccine;

public class VaccineNotFoundException extends RuntimeException{
  VaccineNotFoundException(Long id) {
    super("Could not find vaccine with id: " + id);
  }
}
