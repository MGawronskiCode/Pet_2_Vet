package pl.petlovers.Pet2Vet.exceptions.unautorized_exceptions;

public class PetUnauthorizedAttemptException extends UnauthorizedAttemptException {
  public PetUnauthorizedAttemptException(String message) {
    super(message);
  }

  public PetUnauthorizedAttemptException(long userId, long petId) {
    super("User with id = " + userId + " has no pet with id = " + petId);
  }
}
