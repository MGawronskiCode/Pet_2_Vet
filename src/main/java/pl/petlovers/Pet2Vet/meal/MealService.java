package pl.petlovers.Pet2Vet.meal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.exceptions.not_found_exceptions.MealNotFoundException;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.pet.PetRepository;

import java.util.List;

@Slf4j
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
        log.info("Fetching meals for the pet with id = " + petId);
        return petRepository.getById(petId).getMeals();
    }

    public Meal get(long petId, long mealId) {//fixme unused petId
        log.info("Fetching meal with id = " + mealId);
        return mealRepository.findById(mealId)
                .orElseThrow(() -> new MealNotFoundException(mealId));
    }

    public Meal create(long petId, Meal meal) {
        log.info("Fetching pet with id = " + petId);
        Pet pet = petRepository.getById(petId);
        log.info("Creating " + meal.toString());
        pet.addMeal(meal);
        mealRepository.save(meal);
        return meal;
    }

    public Meal update(long petId, long id, Meal meal) {
        log.info("Fetching meal with id = " + id);
        Meal mealFromDb = get(petId, id);
        log.info("Updating of " + mealFromDb.toString() + " to " + meal.toString());
        mealFromDb.modify(meal);
        return mealRepository.save(mealFromDb);
    }

    public void delete(long petId, long id) {
        mealRepository.delete(get(petId, id));
        log.info("Deleting meal with id = " + id);
    }
}
