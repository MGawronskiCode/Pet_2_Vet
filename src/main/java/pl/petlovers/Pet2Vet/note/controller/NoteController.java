package pl.petlovers.Pet2Vet.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.note.NoteService;
import pl.petlovers.Pet2Vet.security.users.AppUserDetails;

import java.util.List;

@RestController
@CrossOrigin
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/notes")
    public List<NoteDTO> getAllUserNotes(@AuthenticationPrincipal AppUserDetails loggedUser) {

        long userId = loggedUser.getId();

        return noteService.getAllUserNotes(userId)
            .stream()
            .map(NoteDTO::of)
            .toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pets/{petId}/notes")
    public List<NoteDTO> getAllPetNotes(@PathVariable long petId) {
        return noteService.getAllPetNotes(petId)
                .stream()
                .map(NoteDTO::of)
                .toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pets/notes")
    public List<NoteDTO> getAllUserPetsNotes(@AuthenticationPrincipal AppUserDetails loggedUser) {

        long userId = loggedUser.getId();

        return noteService.getAllUserPetsNotes(userId)
                .stream()
                .map(NoteDTO::of)
                .toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pets/{petId}/notes/{noteId}")
    public NoteDTO getPetNote(@PathVariable long petId, @PathVariable long noteId) {
        return NoteDTO.of(noteService.getPetNote(petId, noteId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/notes/{noteId}")
    public NoteDTO getUserNote(@AuthenticationPrincipal AppUserDetails loggedUser, @PathVariable long noteId) {

        long userId = loggedUser.getId();

        return NoteDTO.of(noteService.getUserNote(userId, noteId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/pets/{petId}/notes")
    public NoteDTO createPetNote(@PathVariable long petId, @RequestBody NoteDTO noteDTO) {
        return NoteDTO.of(noteService.createPetNote(petId, noteDTO.toNote()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/notes")
    public NoteDTO createUserNote(@AuthenticationPrincipal AppUserDetails loggedUser, @RequestBody NoteDTO noteDTO) {

        long userId = loggedUser.getId();

        return NoteDTO.of(noteService.createUserNote(userId, noteDTO.toNote()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/pets/{petId}/notes/{noteId}")
    public NoteDTO updatePetNote(@PathVariable long petId, @PathVariable long noteId, @RequestBody NoteDTO noteDTO) {
        return NoteDTO.of(noteService.updatePetNote(petId, noteId, noteDTO.toNote()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/notes/{noteId}")
    public NoteDTO updateUserNote(@AuthenticationPrincipal AppUserDetails loggedUser, @PathVariable long noteId, @RequestBody NoteDTO noteDTO) {

        long userId = loggedUser.getId();

        return NoteDTO.of(noteService.updateUserNote(userId, noteId, noteDTO.toNote()));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/notes")
    public void deleteUserNotes(@AuthenticationPrincipal AppUserDetails loggedUser) {

        long userId = loggedUser.getId();

        noteService.deleteUserNotes(userId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/pets/{petId}/notes")
    public void deletePetNotes(@PathVariable long petId) {
        noteService.deletePetNotes(petId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/pets/{petId}/notes/{noteId}")
    public void deletePetNote(@PathVariable long petId, @PathVariable long noteId) {
        noteService.deletePetNote(petId, noteId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/notes/{noteId}")
    public void deleteUserNote(@AuthenticationPrincipal AppUserDetails loggedUser, @PathVariable long noteId) {

        long userId = loggedUser.getId();

        noteService.deleteUserNote(userId, noteId);
    }
}
