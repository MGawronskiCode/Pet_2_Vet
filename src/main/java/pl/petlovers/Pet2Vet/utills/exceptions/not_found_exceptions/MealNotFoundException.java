package pl.petlovers.Pet2Vet.utills.exceptions.not_found_exceptions;

public class MealNotFoundException extends NotFoundInDatabaseException {
  public MealNotFoundException(Long mealId) {
    super("Could not find meal with id: " + mealId);
  }
}
