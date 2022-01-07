package pl.petlovers.Pet2Vet.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.note.NoteService;

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
    @GetMapping("/users/{userId}/notes")
    public List<NoteDTO> getAllUserNotes(@PathVariable long userId) {
        return noteService.getAllUserNotes(userId)
                .stream()
                .map(NoteDTO::of)
                .toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("users/{userId}/pets/notes")
    public List<NoteDTO> getAllUserPetsNotes(@PathVariable long userId) {
        return noteService.getAllUserPetsNotes(userId)
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
    @GetMapping("/users/{userId}/notes/{noteId}")
    public NoteDTO getUserNote(@PathVariable long userId, @PathVariable long noteId) {
        return NoteDTO.of(noteService.getUserNote(userId, noteId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pets/{petId}/notes/{noteId}")
    public NoteDTO getPetNote(@PathVariable long petId, @PathVariable long noteId) {
        return NoteDTO.of(noteService.getPetNote(petId, noteId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/notes")
    public NoteDTO createUserNote(@PathVariable long userId, @RequestBody NoteDTO noteDTO) {
        return NoteDTO.of(noteService.createUserNote(userId, noteDTO.toNote()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/pets/{petId}/notes")
    public NoteDTO createPetNote(@PathVariable long petId, @RequestBody NoteDTO noteDTO) {
        return NoteDTO.of(noteService.createPetNote(petId, noteDTO.toNote()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/users/{userId}/notes/{noteId}")
    public NoteDTO updateUserNote(@PathVariable long userId, @PathVariable long noteId, @RequestBody NoteDTO noteDTO) {
        return NoteDTO.of(noteService.updateUserNote(userId, noteId, noteDTO.toNote()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/pets/{petId}/notes/{noteId}")
    public NoteDTO updatePetNote(@PathVariable long petId, @PathVariable long noteId, @RequestBody NoteDTO noteDTO) {
        return NoteDTO.of(noteService.updatePetNote(petId, noteId, noteDTO.toNote()));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/users/{userId}/notes")
    public void deleteUserNotes(@PathVariable long userId) {
        noteService.deleteUserNotes(userId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/users/{userId}/notes/{noteId}")
    public void deleteUserNote(@PathVariable long userId, @PathVariable long noteId) {
        noteService.deleteUserNote(userId, noteId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/pets/{petId}/notes")
    public void deletePetNotes(@PathVariable long petId) {
        noteService.deletePetNotes(petId);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/pets/{petId}/notes/{noteId}")
    public ResponseEntity<Long> deletePetNote(@PathVariable long petId, @PathVariable long noteId) {
        noteService.deletePetNote(petId, noteId);
        return new ResponseEntity<>(noteId, HttpStatus.OK);
    }
}
