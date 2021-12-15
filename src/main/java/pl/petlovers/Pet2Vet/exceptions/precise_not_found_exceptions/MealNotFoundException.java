package pl.petlovers.Pet2Vet.exceptions.precise_not_found_exceptions;

import pl.petlovers.Pet2Vet.exceptions.NotFoundInDatabaseException;

public class MealNotFoundException extends NotFoundInDatabaseException {
    public MealNotFoundException(Long mealId) {
        super("Could not find meal with id: " + mealId);
    }
}
