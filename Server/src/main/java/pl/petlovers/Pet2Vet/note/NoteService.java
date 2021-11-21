package pl.petlovers.Pet2Vet.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.appUser.AppUserRepository;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.pet.PetRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final AppUserRepository userRepository;
    private final PetRepository petRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository, AppUserRepository userRepository, PetRepository petRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.petRepository = petRepository;
    }

    public List<Note> getAllUserNotes(long userId) {
        return userRepository.findById(userId).orElseThrow().getNotes();
    }

    public List<Note> getAllUserPetsNotes(long userId) {
        AppUser user = userRepository.getById(userId);
        List<Pet> pets = user.getPets();
        return pets.stream()
                .map(Pet::getNotes)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<Note> getAllPetNotes(Long petId) {
        return petRepository.findById(petId).orElseThrow().getNotes();
    }

    public Note get(long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
    }

    public Note create(Note note) {
        note.setCreated(LocalDateTime.now());
        return noteRepository.save(note);
    }

    public Note update(long id, Note note) {
        Note noteFromDb = get(id);
        noteFromDb.modify(note);
        return noteRepository.save(noteFromDb);
    }

    public void delete(long id) {
        noteRepository.delete(get(id));
    }
}
