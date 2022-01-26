package pl.petlovers.Pet2Vet.entities.meal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.utills.exceptions.forbidden_exceptions.MealForbiddenAccessException;
import pl.petlovers.Pet2Vet.entities.meal.MealService;
import pl.petlovers.Pet2Vet.entities.pet.Pet;
import pl.petlovers.Pet2Vet.entities.pet.PetService;
import pl.petlovers.Pet2Vet.utills.security.users.AppUserDetails;

import java.util.List;

@RestController
@CrossOrigin
public class MealController {

  private final MealService mealService;
  private final PetService petService;

  @Autowired
  public MealController(MealService mealService, PetService petService) {
    this.mealService = mealService;
    this.petService = petService;
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/pets/{petId}/meals")
  public List<MealDTO> getAll(@PathVariable long petId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrContainsPet(petId, loggedUser)) {

      return mealService.getAll(petId)
          .stream()
          .map(MealDTO::of)
          .toList();
    } else {

      throw new MealForbiddenAccessException();
    }
  }

  private boolean loggedUserIsAdminOrContainsPet(long petId, AppUserDetails loggedUser) {

    return loggedUser.isAdmin() || loggedUserContainsPet(petId, loggedUser);
  }

  private boolean loggedUserContainsPet(long petId, AppUserDetails loggedUser) {

    Pet pet = petService.get(petId);

    return loggedUser.getAppUser().getPets().contains(pet);
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/pets/{petId}/meals/{mealId}")
  public MealDTO get(@PathVariable long petId, @PathVariable long mealId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrContainsPet(petId, loggedUser)) {

      return MealDTO.of(mealService.getPetMeal(petId, mealId));
    } else {

      throw new MealForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/pets/{petId}/meals")
  public MealDTO create(@PathVariable long petId, @RequestBody MealDTO mealDTO, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrContainsPet(petId, loggedUser)) {

      return MealDTO.of(mealService.create(petId, mealDTO.toMeal()));
    } else {

      throw new MealForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.CREATED)
  @PutMapping("/pets/{petId}/meals/{mealId}")
  public MealDTO update(@PathVariable long petId, @PathVariable long mealId, @RequestBody MealDTO mealDTO, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrContainsPet(petId, loggedUser)) {

      return MealDTO.of(mealService.update(petId, mealId, mealDTO.toMeal()));
    } else {

      throw new MealForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/pets/{petId}/meals/{mealId}")
  public void delete(@PathVariable long petId, @PathVariable long mealId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrContainsPet(petId, loggedUser)) {

      mealService.delete(petId, mealId);
    } else {

      throw new MealForbiddenAccessException();
    }
  }
}
