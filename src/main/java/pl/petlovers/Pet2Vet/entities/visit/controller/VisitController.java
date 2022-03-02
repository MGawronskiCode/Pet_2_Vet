package pl.petlovers.Pet2Vet.visit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.appUser.AppUserService;
import pl.petlovers.Pet2Vet.exceptions.forbidden_exceptions.VisitForbiddenAccessException;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.pet.PetService;
import pl.petlovers.Pet2Vet.security.users.AppUserDetails;
import pl.petlovers.Pet2Vet.visit.VisitService;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class VisitController {

  private final VisitService visitService;
  private final PetService petService;
  private final AppUserService appUserService;

  @Autowired
  public VisitController(VisitService visitService, PetService petService, AppUserService appUserService) {
    this.visitService = visitService;
    this.petService = petService;
    this.appUserService = appUserService;
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{petId}/visits")
  public List<VisitDTO> getAll(@PathVariable long petId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrVisitOfHisPet(petId, loggedUser)) {

      return visitService.getAll(petId)
          .stream()
          .map(VisitDTO::of)
          .toList();
    } else {

      throw new VisitForbiddenAccessException();
    }
  }

  private boolean loggedUserIsAdminOrVisitOfHisPet(long petId, AppUserDetails loggedUser) {

    return loggedUser.isAdmin() || visitOfLoggedUserPet(petId, loggedUser);
  }

  private boolean visitOfLoggedUserPet(long petId, AppUserDetails loggedUser) {

    Pet pet = petService.get(petId);

    return petOfLoggedUser(loggedUser, pet);
  }

  private boolean petOfLoggedUser(AppUserDetails loggedUser, Pet pet) {
    AppUser user = appUserService.get(loggedUser.getAppUser().getId());
    return user.getPets().contains(pet);
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{petId}/visits/{visitId}")
  public VisitDTO get(@PathVariable long petId, @PathVariable long visitId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrVisitOfHisPet(petId, loggedUser)) {

      return VisitDTO.of(visitService.get(visitId));
    } else {

      throw new VisitForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/{petId}/visits")
  public VisitDTO create(@PathVariable long petId, @RequestBody VisitDTO visitDTO, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrVisitOfHisPet(petId, loggedUser)) {

      return VisitDTO.of(visitService.create(petId, visitDTO.toVisit()));
    } else {

      throw new VisitForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.CREATED)
  @PutMapping("/{petId}/visits/{visitId}")
  public VisitDTO update(@PathVariable long petId, @PathVariable long visitId, @RequestBody VisitDTO visitDTO, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrVisitOfHisPet(petId, loggedUser)) {

      return VisitDTO.of(visitService.update(visitId, visitDTO.toVisit()));
    } else {

      throw new VisitForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{petId}/visits/{visitId}")
  public ResponseEntity<Long> delete(@PathVariable long petId, @PathVariable long visitId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrVisitOfHisPet(petId, loggedUser)) {

      visitService.delete(visitId);
      return new ResponseEntity<>(visitId, HttpStatus.OK);
    } else {

      throw new VisitForbiddenAccessException();
    }
  }
}
