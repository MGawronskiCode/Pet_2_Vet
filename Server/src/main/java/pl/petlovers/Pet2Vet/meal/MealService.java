package pl.petlovers.Pet2Vet.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.pet.PetRepository;

import java.util.List;

@Service
public class MealService {

    private final PetRepository petRepository;
    private final MealRepository mealRepository;

    @Autowired
    public MealService(PetRepository petRepository, MealRepository mealRepository) {
        this.petRepository = petRepository;
        this.mealRepository = mealRepository;
    }

    public List<Meal> getAll(long petId) {
        return petRepository.getById(petId).getMeals();
    }

    public Meal get(long petId, long mealId) {
        return mealRepository.findById(mealId)
                .orElseThrow(() -> new MealNotFoundException(mealId));
    }

    public Meal create(long petId, Meal meal) {
        Pet pet = petRepository.getById(petId);
        pet.addMeal(meal);
        mealRepository.save(meal);

        return meal;
    }

    public Meal update(long petId, long id, Meal meal) {
        Meal mealFromDb = get(petId, id);
        mealFromDb.modify(meal);
        return mealRepository.save(mealFromDb);
    }

    public void delete(long petId, long id) {
        mealRepository.delete(get(petId, id));
    }
}
