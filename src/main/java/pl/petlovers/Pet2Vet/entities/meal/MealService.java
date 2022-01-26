package pl.petlovers.Pet2Vet.entities.meal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.utills.exceptions.not_found_exceptions.MealNotFoundException;
import pl.petlovers.Pet2Vet.utills.exceptions.not_found_exceptions.PetNotFoundException;
import pl.petlovers.Pet2Vet.entities.pet.Pet;
import pl.petlovers.Pet2Vet.entities.pet.PetRepository;

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
    Pet pet = getPet(petId);
    if (pet.getId() != petId) {

      throw new PetNotFoundException(petId);
    }

    return petRepository.getById(petId).getMeals()
        .stream()
        .filter(meals -> !meals.isDeleted())
        .toList();
  }

  private Pet getPet(long petId) {
    log.info("Fetching pet with id = " + petId);

    return petRepository.findById(petId)
        .orElseThrow(() -> new PetNotFoundException(petId));

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
    Meal mealFromDb = getPetMeal(petId, id);
    log.info("Updating of " + mealFromDb.toString() + " to " + meal.toString());
    mealFromDb.modify(meal);

    return mealRepository.save(mealFromDb);
  }

  public Meal getPetMeal(long petId, long mealId) {
    log.info("Fetching pet's meal");
    Meal meal = get(mealId);

    if ((meal.getPet().getId() != petId) || (meal.isDeleted())) {

      throw new MealNotFoundException(mealId);
    }

    return meal;
  }

  public Meal get(long mealId) {

    log.info("Fetching meal with id = " + mealId);

    final Meal meal = mealRepository.findById(mealId)
        .orElseThrow(() -> new MealNotFoundException(mealId));

    if (meal.isDeleted()) {

      throw new MealNotFoundException(mealId);
    } else {

      return meal;
    }
  }

  public void delete(long petId, long id) {
    log.info("Deleting meal with id = " + id);
    Meal mealFromDb = getPetMeal(petId, id);
    mealFromDb.delete();

    mealRepository.save(mealFromDb);
  }

}
