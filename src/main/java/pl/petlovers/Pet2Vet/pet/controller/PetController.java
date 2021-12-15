package pl.petlovers.Pet2Vet.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.appUser.AppUserService;
import pl.petlovers.Pet2Vet.exceptions.unautorized_exceptions.PetUnauthorizedAttemptException;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.pet.PetService;

import java.util.List;

@RestController
@CrossOrigin
public class PetController {

  private final PetService petService;
  private final AppUserService appUserService;

  @Autowired
  public PetController(PetService petService, AppUserService appUserService) {
    this.petService = petService;
    this.appUserService = appUserService;
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/pets")
  public List<PetDTO> get() {
    return petService.getAll()
        .stream()
        .map(PetDTO::of)
        .toList();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/pets/{petId}")
  public PetDTO get(@PathVariable long petId) {
    return PetDTO.of(petService.get(petId));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/pets")
  public PetDTO create(@RequestBody PetDTO petDTO) {
    return PetDTO.of(petService.create(petDTO.toPet()));
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PutMapping("/pets/{petId}")
  public PetDTO update(@PathVariable long petId, @RequestBody PetDTO petDTO) {
    return PetDTO.of(petService.update(petId, petDTO.toPet()));
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/pets/{petId}")
  public void cancel(@PathVariable long petId) {
    petService.delete(petId);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("users/{userId}/pets/{petId}")
  public PetDTO get(@PathVariable long userId, @PathVariable long petId) {
    if (userHasPetWithId(userId, petId)) {

      return PetDTO.of(petService.get(petId));
    } else {
      throw new PetUnauthorizedAttemptException(userId, petId);
    }
  }

//  todo create (@PathVariable long userId, @PathVariable long petId)
//  todo update (@PathVariable long userId, @PathVariable long petId)

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("users/{userId}/pets/{petId}")
  public void cancel(@PathVariable long userId, @PathVariable long petId) {
    if (userHasPetWithId(userId, petId)) {
      petService.delete(petId);
    } else {
      throw new PetUnauthorizedAttemptException(userId, petId);
    }
  }


  private boolean userHasPetWithId(long userId, long petId) {
    AppUser user = appUserService.get(userId);
    for (Pet pet : user.getPets()) {
      if (pet.getId() == petId) {
        return true;
      }
    }

    return false;
  }
}
