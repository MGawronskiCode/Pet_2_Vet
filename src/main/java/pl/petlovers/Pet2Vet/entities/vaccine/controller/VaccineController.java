package pl.petlovers.Pet2Vet.entities.vaccine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.utills.exceptions.forbidden_exceptions.VaccineForbiddenAccessException;
import pl.petlovers.Pet2Vet.entities.pet.Pet;
import pl.petlovers.Pet2Vet.entities.pet.PetService;
import pl.petlovers.Pet2Vet.utills.security.users.AppUserDetails;
import pl.petlovers.Pet2Vet.entities.vaccine.VaccineService;

import java.util.List;

@RestController
@CrossOrigin
public class VaccineController {

  private final VaccineService vaccineService;
  private final PetService petService;

  @Autowired
  public VaccineController(VaccineService vaccineService, PetService petService) {
    this.vaccineService = vaccineService;
    this.petService = petService;
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("pets/{petId}/vaccines")
  public List<VaccineDTO> getPetVaccines(@PathVariable long petId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrVaccineOfHisPet(petId, loggedUser)) {

      return vaccineService.getPetVaccines(petId)
          .stream()
          .map(VaccineDTO::of)
          .toList();
    } else {

      throw new VaccineForbiddenAccessException();
    }
  }

  private boolean loggedUserIsAdminOrVaccineOfHisPet(long petId, AppUserDetails loggedUser) {

    return loggedUser.isAdmin() || vaccineOfLoggedUserPet(petId, loggedUser);
  }

  private boolean vaccineOfLoggedUserPet(long petId, AppUserDetails loggedUser) {

    Pet pet = petService.get(petId);

    return loggedUserPet(loggedUser, pet);
  }

  private boolean loggedUserPet(AppUserDetails loggedUser, Pet pet) {
    return loggedUser.getAppUser().getPets().contains(pet);
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("pets/{petId}/vaccines/{vaccineId}")
  public VaccineDTO getPetVaccine(
      @PathVariable long petId,
      @PathVariable long vaccineId,
      @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrVaccineOfHisPet(petId, loggedUser)) {

      return VaccineDTO.of(vaccineService.getPetVaccine(petId, vaccineId));
    } else {

      throw new VaccineForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("pets/{petId}/vaccines")
  public VaccineDTO createVaccineInPet(
      @PathVariable long petId,
      @RequestBody VaccineDTO vaccineDTO,
      @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrVaccineOfHisPet(petId, loggedUser)) {

      return VaccineDTO.of(vaccineService.createVaccineInPet(petId, vaccineDTO.toVaccine()));
    } else {

      throw new VaccineForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.ACCEPTED)
  @PutMapping("pets/{petId}/vaccines/{vaccineId}")
  public VaccineDTO updatePetVaccine(
      @PathVariable long petId,
      @PathVariable long vaccineId,
      @RequestBody VaccineDTO vaccineDTO,
      @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrVaccineOfHisPet(petId, loggedUser)) {

      return VaccineDTO.of(vaccineService.updatePetVaccine(petId, vaccineId, vaccineDTO.toVaccine()));
    } else {

      throw new VaccineForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("pets/{petId}/vaccines/{vaccineId}")
  public void cancelPetVaccine(
      @PathVariable long petId,
      @PathVariable long vaccineId,
      @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrVaccineOfHisPet(petId, loggedUser)) {

      vaccineService.deletePetVaccine(petId, vaccineId);
    } else {

      throw new VaccineForbiddenAccessException();
    }
  }
}
