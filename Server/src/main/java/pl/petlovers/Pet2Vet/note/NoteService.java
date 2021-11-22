package pl.petlovers.Pet2Vet.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.appUser.AppUserNotFoundException;
import pl.petlovers.Pet2Vet.appUser.AppUserRepository;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.pet.PetNotFoundException;
import pl.petlovers.Pet2Vet.pet.PetRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
        return getUser(userId).getNotes();
    }

    private AppUser getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AppUserNotFoundException(userId));
    }

    public List<Note> getAllUserPetsNotes(long userId) {
        AppUser user = getUser(userId);
        List<Pet> pets = getPets(user);
        return pets.stream()
                .map(Pet::getNotes)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Pet> getPets(AppUser user) {
        List<Pet> pets = user.getPets();
        if (pets.size() == 0) {
            throw new IllegalStateException("No pet was found.");
        }
        return pets;
    }

    public List<Note> getAllPetNotes(long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException(petId))
                .getNotes();
    }

    public Note getUserNote(long userId, long noteId) {
        Note note = getNote(noteId);
        if (note.getAppUser().getId() != userId) {
            throw new AppUserNotFoundException(userId);
        }
        return note;
    }

    public Note getPetNote(long petId, long noteId) {
        Note note = getNote(noteId);
        if (note.getPet().getId() != petId) {
            throw new PetNotFoundException(petId);
        }
        return note;
    }

    private Note getNote(long noteId) {
        Optional<Note> noteOptional = noteRepository.findById(noteId);
        if (noteOptional.isEmpty()) {
            throw new NoteNotFoundException(noteId);
        }
        return noteOptional.get();
    }

    public Note createUserNote(long userId, Note note) {
        AppUser user = getUser(userId);
        user.addNote(note);
        noteRepository.save(note);
        return note;
    }

    public Note createPetNote(long petId, Note note) {
        Pet pet = getPet(petId);
        pet.addNote(note);
        noteRepository.save(note);
        return note;
    }

    private Pet getPet(long petId) {
        Optional<Pet> petOptional = petRepository.findById(petId);
        if (petOptional.isEmpty()){
            throw new PetNotFoundException(petId);
        }
        return petOptional.get();
    }


    /*
        In Progress
     */


//    public Note update(long id, Note note) {
//        Note noteFromDb = get(userId, id);
//        noteFromDb.modify(note);
//        return noteRepository.save(noteFromDb);
//    }
//
//    public void delete(long id) {
//        noteRepository.delete(get(userId, id));
//    }
}
