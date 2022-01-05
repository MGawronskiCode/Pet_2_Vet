package pl.petlovers.Pet2Vet.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.note.Note;
import pl.petlovers.Pet2Vet.note.NoteRepository;

import java.util.List;

@Service
public class NoteFileService {

  private final FileRepository fileRepository;
  private final NoteRepository noteRepository;

  @Autowired
  public NoteFileService(FileRepository fileRepository, NoteRepository noteRepository) {
    this.fileRepository = fileRepository;
    this.noteRepository = noteRepository;
  }

  public List<File> getAll(long noteId) { return noteRepository.getById(noteId).getFiles();}

  public File get(long fileId) {
    return fileRepository.getById(fileId) ;
  }

  public File create(long noteId, File file) {
    Note note = noteRepository.getById(noteId);
    note.addFile(file);
    return fileRepository.save(file);
  }

  public File update(long noteId, long fileId, File file) { //fixme unused noteId
    File fileFromDb = get(fileId);
    fileFromDb.modify(file);
    return fileRepository.save(fileFromDb);
  }

  public void delete(long fileId) {
    fileRepository.delete(get(fileId));
  }
}
