package pl.petlovers.Pet2Vet.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.note.NoteService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
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
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("users/{userId}/pets/notes")
    public List<NoteDTO> getAllUserPetsNotes(@PathVariable long userId) {
        return noteService.getAllUserPetsNotes(userId)
                .stream()
                .map(NoteDTO::of)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pets/{petId}/notes")
    public List<NoteDTO> getAllPetNotes(@PathVariable long petId) {
        return noteService.getAllPetNotes(petId)
                .stream()
                .map(NoteDTO::of)
                .collect(Collectors.toList());
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











    /*
        In Progress
     */


//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping
//    public NoteDTO create(@RequestBody NoteDTO noteDTO) {
//        return NoteDTO.of(noteService.create(noteDTO.toNote()));
//    }
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PutMapping("/{noteId}")
//    public NoteDTO update(@PathVariable long noteId, @RequestBody NoteDTO noteDTO) {
//        return NoteDTO.of(noteService.update(noteId, noteDTO.toNote()));
//    }
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @DeleteMapping("/{noteId}")
//    public void delete(@PathVariable long noteId) {
//        noteService.delete(noteId);
//    }

}
