package pl.petlovers.Pet2Vet.meal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.meal.MealService;

import java.util.List;

@RestController
@CrossOrigin
public class MealController {

    private final MealService mealService;

    @Autowired
    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pets/{petId}/meals")
    public List<MealDTO> getAll(@PathVariable long petId) {
        return mealService.getAll(petId)
                .stream()
                .map(MealDTO::of)
                .toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pets/{petId}/meals/{mealId}")
    public MealDTO get(@PathVariable long petId, @PathVariable long mealId) {
        return MealDTO.of(mealService.getPetMeal(petId, mealId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/pets/{petId}/meals")
    public MealDTO create(@PathVariable long petId, @RequestBody MealDTO mealDTO) {
        return MealDTO.of(mealService.create(petId, mealDTO.toMeal()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/pets/{petId}/meals/{mealId}")
    public MealDTO update(@PathVariable long petId, @PathVariable long mealId, @RequestBody MealDTO mealDTO) {
        return MealDTO.of(mealService.update(petId, mealId, mealDTO.toMeal()));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/pets/{petId}/meals/{mealId}")
    public void delete(@PathVariable long petId, @PathVariable long mealId) {
        mealService.delete(petId, mealId);
    }
}
