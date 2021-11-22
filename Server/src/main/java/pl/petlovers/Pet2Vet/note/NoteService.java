package pl.petlovers.Pet2Vet.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.appUser.AppUserNotFoundException;
import pl.petlovers.Pet2Vet.appUser.AppUserRepository;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.pet.PetNotFoundException;
import pl.petlovers.Pet2Vet.pet.PetRepository;

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
        return getPet(petId).getNotes();
    }

    private Pet getPet(long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException(petId));
    }

    public Note getUserNote(long userId, long noteId) {
        Note note = getNote(noteId);
        if (note.getAppUser().getId() != userId) {
            throw new IllegalArgumentException("Wrong user ID");
        }
        return note;
    }

    private Note getNote(long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException(noteId));
    }

    public Note getPetNote(long petId, long noteId) {
        Note note = getNote(noteId);
        if (note.getPet().getId() != petId) {
            throw new IllegalArgumentException("Wrong pet ID");
        }
        return note;
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

    public Note updateUserNote(long userId, long noteId, Note newData) {
        Note noteFromDb = getUserNote(userId, noteId);
        return getModifiedNote(newData, noteFromDb);
    }

    private Note getModifiedNote(Note newData, Note noteFromDb) {
        noteFromDb.modify(newData);
        noteRepository.save(noteFromDb);
        return noteFromDb;
    }

    public Note updatePetNote(long petId, long noteId, Note newData) {
        Note noteFromDb = getPetNote(petId, noteId);
        return getModifiedNote(newData, noteFromDb);
    }

    public void deleteUserNote(long userId, long noteId) {
        Note noteFromDb = getUserNote(userId, noteId);
        noteRepository.delete(noteFromDb);
    }

    public void deletePetNote(long petId, long noteId) {
        Note noteFromDb = getPetNote(petId, noteId);
        noteRepository.delete(noteFromDb);
    }
}
