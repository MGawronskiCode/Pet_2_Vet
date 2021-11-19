package pl.petlovers.Pet2Vet.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.note.NoteService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<NoteDTO> getAll() {
        return noteService.getAll()
                .stream()
                .map(NoteDTO::of)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{noteId}")
    public NoteDTO get(@PathVariable long noteId) {
        return NoteDTO.of(noteService.get(noteId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public NoteDTO create(@RequestBody NoteDTO noteDTO) {
        return NoteDTO.of(noteService.create(noteDTO.toNote()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{noteId}")
    public NoteDTO update(@PathVariable long noteId, @RequestBody NoteDTO noteDTO) {
        return NoteDTO.of(noteService.update(noteId, noteDTO.toNote()));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{noteId}")
    public void delete(@PathVariable long noteId) {
        noteService.delete(noteId);
    }

}
