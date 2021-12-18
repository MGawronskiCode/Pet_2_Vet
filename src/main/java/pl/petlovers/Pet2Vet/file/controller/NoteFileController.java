package pl.petlovers.Pet2Vet.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.file.NoteFileService;
import pl.petlovers.Pet2Vet.note.NoteService;

import java.util.List;

@RestController
public class NoteFileController {

  private final NoteFileService noteFileService;
  private final NoteService noteService;

  @Autowired
  public NoteFileController(NoteFileService noteFileService, NoteService noteService) {
    this.noteFileService = noteFileService;
    this.noteService = noteService;
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/notes/{noteId}/files")
  public List<FileDTO> getAll(@PathVariable long noteId) {
    return noteFileService.getAll(noteId)
      .stream()
      .map(FileDTO::of)
      .toList();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/notes/{noteId}/files/{fileId}")
  public FileDTO get(@PathVariable long noteId, @PathVariable long fileId) {
    if (noteService.getNote(noteId).containsFile(fileId)) {
      return FileDTO.of(noteFileService.get(fileId));
    }
    throw new IllegalArgumentException("You naughty naughty user, no hacking here!");
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/notes/{noteId}/files")
  public FileDTO create(@PathVariable long noteId, @RequestBody FileDTO fileDTO) {
    return FileDTO.of(noteFileService.create(noteId, fileDTO.toFile()));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PutMapping("/notes/{noteId}/files/{fileId}")
  public FileDTO update(@PathVariable long noteId, @PathVariable long fileId, @RequestBody FileDTO fileDTO) {
    if (noteService.getNote(noteId).containsFile(fileId)) {
      return FileDTO.of(noteFileService.update(noteId, fileId, fileDTO.toFile()));
    }
    throw new IllegalArgumentException("You naughty naughty user, no hacking here!");
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/notes/{noteId}/files/{fileId}")
  public void delete(@PathVariable long noteId, @PathVariable long fileId) {
    if (noteService.getNote(noteId).containsFile(fileId)) {
      noteFileService.delete(fileId);
    }
    throw new IllegalArgumentException("You naughty naughty user, no hacking here!");
  }
}


