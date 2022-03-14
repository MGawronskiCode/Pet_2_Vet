package pl.petlovers.Pet2Vet.utills.exceptions;

public class PasswordNonValidPatternException extends RuntimeException {
  public PasswordNonValidPatternException() {
    super("Hasło musi mieć co najmniej 4 znaki i zawierać co najmniej jedną cyfrę.");
  }
}
