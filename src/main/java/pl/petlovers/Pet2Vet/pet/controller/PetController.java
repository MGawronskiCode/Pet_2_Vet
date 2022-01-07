package pl.petlovers.Pet2Vet.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.exceptions.forbidden_exceptions.PetForbiddenAccessException;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.pet.PetService;
import pl.petlovers.Pet2Vet.security.users.AppUserDetails;

import java.util.List;

@RestController
@CrossOrigin
public class PetController {

  private final PetService petService;

  @Autowired
  public PetController(PetService petService) {
    this.petService = petService;
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/pets")
  public List<PetDTO> get(@AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUser.isAdmin()) {
      return petService.getAll()
          .stream()
          .map(PetDTO::of)
          .toList();
    } else {
      return petService.getAll()
          .stream()
          .filter(pet -> loggedUserHaveThisPet(pet, loggedUser))
          .map(PetDTO::of)
          .toList();
    }
  }

  private boolean loggedUserHaveThisPet(Pet pet, AppUserDetails loggedUser) {
    return pet.getAppUsers()
        .stream()
        .anyMatch(user -> user.getId().equals(loggedUser.getAppUser().getId()));
  }


  @Secured({"ROLE_ADMIN", "ROLE_OWNER"})
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/pets")
  public PetDTO create(@RequestBody PetDTO petDTO, @AuthenticationPrincipal AppUserDetails loggedUser) {

    return petService.create(loggedUser.getAppUser().getId(), petDTO);
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/pets/{petId}")
  public PetDTO get(@PathVariable long petId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    final Pet pet = petService.get(petId);
    if (loggedUserHaveThisPet(pet, loggedUser) || loggedUser.isAdmin()) {
      return PetDTO.of(pet);
    } else {
      throw new PetForbiddenAccessException(getYouDontHaveThisPetCommunicate(pet.getId()));
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER"})
  @ResponseStatus(HttpStatus.ACCEPTED)
  @PutMapping("/pets/{petId}")
  public PetDTO update(@PathVariable long petId, @RequestBody PetDTO petDTO, @AuthenticationPrincipal AppUserDetails loggedUser) {

    final Pet pet = petService.get(petId);
    if (loggedUserHaveThisPet(pet, loggedUser) || loggedUser.isAdmin()) {
      return PetDTO.of(petService.update(petId, petDTO));
    } else {
      throw new PetForbiddenAccessException(getYouDontHaveThisPetCommunicate(pet.getId()));
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/pets/{petId}")
  public void cancel(@PathVariable long petId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    final Pet pet = petService.get(petId);
    if (loggedUserHaveThisPet(pet, loggedUser) || loggedUser.isAdmin()) {
      petService.delete(petId);
    } else {
      throw new PetForbiddenAccessException(getYouDontHaveThisPetCommunicate(pet.getId()));
    }
  }

  private String getYouDontHaveThisPetCommunicate(long petId) {
    return "Nie masz pupila z ID: " + petId;
  }
}
