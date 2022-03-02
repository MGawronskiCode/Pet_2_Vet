package pl.petlovers.Pet2Vet.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.appUser.AppUserService;
import pl.petlovers.Pet2Vet.exceptions.forbidden_exceptions.NoteForbiddenAccessException;
import pl.petlovers.Pet2Vet.note.NoteService;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.pet.PetService;
import pl.petlovers.Pet2Vet.security.users.AppUserDetails;

import java.util.List;

@RestController
@CrossOrigin
public class NoteController {

    private final NoteService noteService;
    private final PetService petService;
    private final AppUserService appUserService;

    @Autowired
    public NoteController(NoteService noteService, PetService petService, AppUserService appUserService) {
        this.noteService = noteService;
        this.petService = petService;
        this.appUserService = appUserService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/notes")
    public List<NoteDTO> getAllUserNotes(@AuthenticationPrincipal AppUserDetails loggedUser) {

        return noteService.getAllUserNotes(loggedUser.getId())
                .stream()
                .map(NoteDTO::of)
                .toList();
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
        AppUser user = appUserService.get(loggedUser.getAppUser().getId());
        return user.getPets().contains(pet);
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
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/pets/{petId}/notes/{noteId}")
    public ResponseEntity<Long> deletePetNote(@PathVariable long petId, @PathVariable long noteId, @AuthenticationPrincipal AppUserDetails loggedUser) {

        if (loggedUserIsAdminOrNoteBelongsToHisPet(petId, loggedUser)) {

            noteService.deletePetNote(petId, noteId);
            return new ResponseEntity<>(noteId, HttpStatus.OK);
        } else {
            throw new NoteForbiddenAccessException();
        }
    }
}
