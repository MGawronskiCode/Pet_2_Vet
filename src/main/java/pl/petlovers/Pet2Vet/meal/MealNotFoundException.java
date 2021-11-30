package pl.petlovers.Pet2Vet.meal;

public class MealNotFoundException extends RuntimeException {
    public MealNotFoundException(Long mealId) {
        super("Could not find meal with id: " + mealId);
    }
}