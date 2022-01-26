package pl.petlovers.Pet2Vet.entities.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.utills.exceptions.forbidden_exceptions.NoteForbiddenAccessException;
import pl.petlovers.Pet2Vet.entities.note.NoteService;
import pl.petlovers.Pet2Vet.entities.pet.Pet;
import pl.petlovers.Pet2Vet.entities.pet.PetService;
import pl.petlovers.Pet2Vet.utills.security.users.AppUserDetails;

import java.util.List;

@RestController
@CrossOrigin
public class NoteController {

  private final NoteService noteService;
  private final PetService petService;

  @Autowired
  public NoteController(NoteService noteService, PetService petService) {
    this.noteService = noteService;
    this.petService = petService;
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/users/{userId}/notes")
  public List<NoteDTO> getAllUserNotes(@PathVariable long userId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrNoteBelongsToHim(userId, loggedUser)) {

      return noteService.getAllUserNotes(userId)
          .stream()
          .map(NoteDTO::of)
          .toList();
    } else {

      throw new NoteForbiddenAccessException();
    }

  }

  private boolean loggedUserIsAdminOrNoteBelongsToHim(long userId, AppUserDetails loggedUser) {

    return loggedUser.isAdmin() || noteBelongsToLoggedUser(userId, loggedUser);
  }

  private boolean noteBelongsToLoggedUser(long userId, AppUserDetails loggedUser) {

    return loggedUser.getId() == userId;
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("users/{userId}/pets/notes")
  public List<NoteDTO> getAllUserPetsNotes(@PathVariable long userId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrNoteBelongsToHim(userId, loggedUser)) {

      return noteService.getAllUserPetsNotes(userId)
          .stream()
          .map(NoteDTO::of)
          .toList();
    } else {

      throw new NoteForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/pets/{petId}/notes")
  public List<NoteDTO> getAllPetNotes(@PathVariable long petId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrNoteBelongsToHisPet(petId, loggedUser)) {

      return noteService.getAllPetNotes(petId)
          .stream()
          .map(NoteDTO::of)
          .toList();
    } else {
      throw new NoteForbiddenAccessException();
    }
  }

  private boolean loggedUserIsAdminOrNoteBelongsToHisPet(long petId, AppUserDetails loggedUser) {

    return loggedUser.isAdmin() || noteBelongsToPetOfLoggedUser(petId, loggedUser);
  }

  private boolean noteBelongsToPetOfLoggedUser(long petId, AppUserDetails loggedUser) {

    Pet pet = petService.get(petId);

    return loggedUserContainsPet(loggedUser, pet);
  }

  private boolean loggedUserContainsPet(AppUserDetails loggedUser, Pet pet) {
    return loggedUser.getAppUser().getPets().contains(pet);
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/users/{userId}/notes/{noteId}")
  public NoteDTO getUserNote(@PathVariable long userId, @PathVariable long noteId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrNoteBelongsToHim(userId, loggedUser)) {

      return NoteDTO.of(noteService.getUserNote(userId, noteId));
    } else {

      throw new NoteForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/pets/{petId}/notes/{noteId}")
  public NoteDTO getPetNote(@PathVariable long petId, @PathVariable long noteId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrNoteBelongsToHisPet(petId, loggedUser)) {

      return NoteDTO.of(noteService.getPetNote(petId, noteId));
    } else {
      throw new NoteForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/users/{userId}/notes")
  public NoteDTO createUserNote(@PathVariable long userId, @RequestBody NoteDTO noteDTO, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrNoteBelongsToHim(userId, loggedUser)) {

      return NoteDTO.of(noteService.createUserNote(userId, noteDTO.toNote()));

    } else {

      throw new NoteForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/pets/{petId}/notes")
  public NoteDTO createPetNote(@PathVariable long petId, @RequestBody NoteDTO noteDTO, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrNoteBelongsToHisPet(petId, loggedUser)) {

      return NoteDTO.of(noteService.createPetNote(petId, noteDTO.toNote()));
    } else {
      throw new NoteForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.CREATED)
  @PutMapping("/users/{userId}/notes/{noteId}")
  public NoteDTO updateUserNote(@PathVariable long userId, @PathVariable long noteId, @RequestBody NoteDTO noteDTO, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrNoteBelongsToHim(userId, loggedUser)) {

      return NoteDTO.of(noteService.updateUserNote(userId, noteId, noteDTO.toNote()));

    } else {

      throw new NoteForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.CREATED)
  @PutMapping("/pets/{petId}/notes/{noteId}")
  public NoteDTO updatePetNote(@PathVariable long petId, @PathVariable long noteId, @RequestBody NoteDTO noteDTO, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrNoteBelongsToHisPet(petId, loggedUser)) {

      return NoteDTO.of(noteService.updatePetNote(petId, noteId, noteDTO.toNote()));
    } else {
      throw new NoteForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/users/{userId}/notes")
  public void deleteUserNotes(@PathVariable long userId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrNoteBelongsToHim(userId, loggedUser)) {

      noteService.deleteUserNotes(userId);
    } else {

      throw new NoteForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/users/{userId}/notes/{noteId}")
  public void deleteUserNote(@PathVariable long userId, @PathVariable long noteId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrNoteBelongsToHim(userId, loggedUser)) {

      noteService.deleteUserNote(userId, noteId);
    } else {

      throw new NoteForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/pets/{petId}/notes")
  public void deletePetNotes(@PathVariable long petId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrNoteBelongsToHisPet(petId, loggedUser)) {

      noteService.deletePetNotes(petId);
    } else {
      throw new NoteForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/pets/{petId}/notes/{noteId}")
  public void deletePetNote(@PathVariable long petId, @PathVariable long noteId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrNoteBelongsToHisPet(petId, loggedUser)) {

      noteService.deletePetNote(petId, noteId);
    } else {
      throw new NoteForbiddenAccessException();
    }
  }
}
