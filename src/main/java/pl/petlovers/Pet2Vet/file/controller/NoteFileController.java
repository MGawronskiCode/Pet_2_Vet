package pl.petlovers.Pet2Vet.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.exceptions.forbidden_exceptions.FileForbiddenAccessException;
import pl.petlovers.Pet2Vet.file.NoteFileService;
import pl.petlovers.Pet2Vet.note.Note;
import pl.petlovers.Pet2Vet.note.NoteService;
import pl.petlovers.Pet2Vet.security.users.AppUserDetails;

import java.util.List;

@RestController
public class NoteFileController {

  private final NoteFileService noteFileService;
  private final NoteService noteService;

  private static final String NO_HACKING_ALLOWED_COMMUNICATE = "You naughty naughty user, no hacking here!";

  @Autowired
  public NoteFileController(NoteFileService noteFileService, NoteService noteService) {
    this.noteFileService = noteFileService;
    this.noteService = noteService;
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/notes/{noteId}/files")
  public List<FileDTO> getAll(@PathVariable long noteId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrFileBelongsToHim(noteId, loggedUser)) {

      return noteFileService.getAll(noteId)
          .stream()
          .map(FileDTO::of)
          .toList();
    } else {

      throw new FileForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/notes/{noteId}/files/{fileId}")
  public FileDTO get(@PathVariable long noteId, @PathVariable long fileId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrFileBelongsToHim(noteId, loggedUser)) {

      if (noteService.getNote(noteId).containsFile(fileId)) {

        return FileDTO.of(noteFileService.get(fileId));
      }

      throw new IllegalArgumentException(NO_HACKING_ALLOWED_COMMUNICATE);
    } else {

      throw new FileForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/notes/{noteId}/files")
  public FileDTO create(@PathVariable long noteId, @RequestBody FileDTO fileDTO, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrFileBelongsToHim(noteId, loggedUser)) {

      return FileDTO.of(noteFileService.create(noteId, fileDTO.toFile()));
    } else {

      throw new FileForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.CREATED)
  @PutMapping("/notes/{noteId}/files/{fileId}")
  public FileDTO update(@PathVariable long noteId, @PathVariable long fileId, @RequestBody FileDTO fileDTO, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrFileBelongsToHim(noteId, loggedUser)) {

      if (noteService.getNote(noteId).containsFile(fileId)) {

        return FileDTO.of(noteFileService.update(fileId, fileDTO.toFile()));
      }

      throw new IllegalArgumentException(NO_HACKING_ALLOWED_COMMUNICATE);
    } else {

      throw new FileForbiddenAccessException();
    }
  }

  @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/notes/{noteId}/files/{fileId}")
  public void delete(@PathVariable long noteId, @PathVariable long fileId, @AuthenticationPrincipal AppUserDetails loggedUser) {

    if (loggedUserIsAdminOrFileBelongsToHim(noteId, loggedUser)) {

      if (noteService.getNote(noteId).containsFile(fileId)) {

        noteFileService.delete(fileId);
      }

      throw new IllegalArgumentException(NO_HACKING_ALLOWED_COMMUNICATE);
    } else {

      throw new FileForbiddenAccessException();
    }
  }

  private boolean loggedUserIsAdminOrFileBelongsToHim(long noteId, AppUserDetails loggedUser) {

    return loggedUser.isAdmin() || fileAddedToLoggedUserNote(noteId, loggedUser);
  }

  private boolean fileAddedToLoggedUserNote(long noteId, AppUserDetails loggedUser) {

    Note note = noteService.getNote(noteId);

    return noteOfLoggedUser(loggedUser, note);
  }

  private boolean noteOfLoggedUser(AppUserDetails loggedUser, Note note) {
    return loggedUser.getAppUser().getNotes().contains(note);
  }
}


