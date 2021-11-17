package pl.petlovers.Pet2Vet.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAll() {
        return noteRepository.findAll();
    }

    public Note get(long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
    }

    public Note create(Note note){
        note.setCreated(LocalDateTime.now());
        return noteRepository.save(note);
    }

    public Note update(long id, Note note){
        Note noteFromDb = get(id);
        noteFromDb.modify(note);
        return noteRepository.save(noteFromDb);
    }

    public void delete(long id){
        noteRepository.delete(get(id));
    }

}
