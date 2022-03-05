package pl.petlovers.Pet2Vet.entities.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.entities.file.VisitFileService;
import pl.petlovers.Pet2Vet.utills.exceptions.forbidden_exceptions.FileForbiddenAccessException;
import pl.petlovers.Pet2Vet.utills.security.users.AppUserDetails;
import pl.petlovers.Pet2Vet.entities.visit.VisitService;

import java.util.List;

@RestController
public class VisitFileController {

  private static final String NO_HACKING_ALLOWED_COMMUNICATE = "You naughty naughty user, no hacking here!";
  private final VisitFileService visitFileService;
  private final VisitService visitService;

  @Autowired
  public VisitFileController(VisitFileService visitFileService, VisitService visitService) {
    this.visitFileService = visitFileService;
    this.visitService = visitService;
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/visits/{visitId}/files")
  public List<FileDTO> getAll(@PathVariable long visitId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUser.isAdmin() || fileAddedToLoggedUserPetVisit(visitId, loggedUser)) {

      return visitFileService.getAll(visitId)
          .stream()
          .map(FileDTO::of)
          .toList();
    } else {

      throw new FileForbiddenAccessException();
    }
  }

  private boolean fileAddedToLoggedUserPetVisit(long visitId, AppUserDetails loggedUser) {

    final List<Long> countsOfVisitsWithGivenIdInUserPets = getListOfCountsOfVisitsWithGivenIdInUsersPets(visitId, loggedUser);

    for (Long countsOfVisitsWithGivenIdInUserPet : countsOfVisitsWithGivenIdInUserPets) {

      if (countsOfVisitsWithGivenIdInUserPet > 0)

        return true;
    }

    return false;
  }

  private List<Long> getListOfCountsOfVisitsWithGivenIdInUsersPets(long visitId, AppUserDetails loggedUser) {
    return loggedUser.getAppUser().getPets().stream()
        .map(pet -> pet.getVisits().stream()
            .filter(visit -> visit.getId() == visitId)
            .count())
        .toList();
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/visits/{visitId}/files/{fileId}")
  public FileDTO get(@PathVariable long visitId, @PathVariable long fileId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUser.isAdmin() || fileAddedToLoggedUserPetVisit(visitId, loggedUser)) {

      if (visitService.get(visitId).containsFile(fileId)) {

        return FileDTO.of(visitFileService.get(fileId));
      }

      throw new IllegalArgumentException(NO_HACKING_ALLOWED_COMMUNICATE);
    } else {

      throw new FileForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/visits/{visitId}/files")
  public FileDTO create(@PathVariable long visitId, @RequestBody FileDTO fileDTO, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUser.isAdmin() || fileAddedToLoggedUserPetVisit(visitId, loggedUser)) {

      return FileDTO.of(visitFileService.create(visitId, fileDTO.toFile()));

    } else {

      throw new FileForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.CREATED)
  @PutMapping("/visits/{visitId}/files/{fileId}")
  public FileDTO update(@PathVariable long visitId, @PathVariable long fileId, @RequestBody FileDTO fileDTO, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUser.isAdmin() || fileAddedToLoggedUserPetVisit(visitId, loggedUser)) {

      if (visitService.get(visitId).containsFile(fileId)) {

        return FileDTO.of(visitFileService.update(fileId, fileDTO.toFile()));
      }

      throw new IllegalArgumentException(NO_HACKING_ALLOWED_COMMUNICATE);
    } else {

      throw new FileForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/visits/{visitId}/files/{fileId}")
  public void delete(@PathVariable long visitId, @PathVariable long fileId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUser.isAdmin() || fileAddedToLoggedUserPetVisit(visitId, loggedUser)) {

      if (visitService.get(visitId).containsFile(fileId)) {

        visitFileService.delete(fileId);
      }

      throw new IllegalArgumentException(NO_HACKING_ALLOWED_COMMUNICATE);
    } else {

      throw new FileForbiddenAccessException();
    }
  }
}
