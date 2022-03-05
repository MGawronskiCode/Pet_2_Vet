package pl.petlovers.Pet2Vet.entities.note;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.entities.appUser.AppUser;
import pl.petlovers.Pet2Vet.entities.appUser.AppUserRepository;
import pl.petlovers.Pet2Vet.utills.exceptions.not_found_exceptions.AppUserNotFoundException;
import pl.petlovers.Pet2Vet.utills.exceptions.not_found_exceptions.PetNotFoundException;
import pl.petlovers.Pet2Vet.utills.exceptions.not_found_exceptions.NoteNotFoundException;
import pl.petlovers.Pet2Vet.entities.pet.Pet;
import pl.petlovers.Pet2Vet.entities.pet.PetRepository;

import java.util.Collection;
import java.util.List;

@Slf4j
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
    AppUser user = getUser(userId);
    log.info("Fetching all user's notes");

    return user.getNotes()
        .stream()
        .filter(note -> !note.isDeleted())
        .toList();
  }

  private AppUser getUser(long userId) {
    log.info("Fetching user with id = " + userId);

    return userRepository.findById(userId)
        .orElseThrow(() -> new AppUserNotFoundException(userId));
  }

  public List<Note> getAllUserPetsNotes(long userId) {
    AppUser user = getUser(userId);
    List<Pet> pets = getPets(user);
    log.info("Fetching all user pets notes");

    return pets.stream()
        .map(Pet::getNotes)
        .flatMap(Collection::stream)
        .filter(note -> !note.isDeleted())
        .toList();
  }

  private List<Pet> getPets(AppUser user) {
    log.info("Fetching all user's pets");
    List<Pet> pets = user.getPets();
    if (pets.isEmpty()) {

      throw new IllegalStateException("No pet was found.");
    }

    return pets;
  }

  public List<Note> getAllPetNotes(long petId) {
    Pet pet = getPet(petId);
    log.info("Fetching all user pet's notes");

    return pet.getNotes()
        .stream()
        .filter(note -> !note.isDeleted())
        .toList();
  }

  private Pet getPet(long petId) {
    log.info("Fetching user's pet with id = " + petId);

    return petRepository.findById(petId)
        .orElseThrow(() -> new PetNotFoundException(petId));
  }

  public Note createUserNote(long userId, Note note) {
    AppUser user = getUser(userId);
    log.info("Creating user's " + note.toString());
    user.addNote(note);
    noteRepository.save(note);

    return note;
  }

  public Note createPetNote(long petId, Note note) {
    Pet pet = getPet(petId);
    log.info("Creating pet's " + note.toString());
    pet.addNote(note);
    noteRepository.save(note);

    return note;
  }

  public Note updateUserNote(long userId, long noteId, Note newData) {
    Note noteFromDb = getUserNote(userId, noteId);
    log.info("Updating of " + noteFromDb.toString() + " to " + newData.toString());

    return getModifiedNote(newData, noteFromDb);
  }

  public Note getUserNote(long userId, long noteId) {
    log.info("Fetching user's note");
    Note note = getNote(noteId);
    try {
      if ((note.getAppUser().getId() != userId)) {

        throw new IllegalArgumentException("Wrong user ID");
      } else if (note.isDeleted()) {

        throw new NoteNotFoundException(noteId);
      }

      return note;
    } catch (NullPointerException error) {

      throw new NullPointerException("Wrong note ID"); // not User's note but Pet's
//            maybe make Note abstract and create UserNote and PetNote?
    }
  }

  private Note getModifiedNote(Note newData, Note noteFromDb) {
    noteFromDb.modify(newData);
    noteRepository.save(noteFromDb);

    return noteFromDb;
  }

  public Note getNote(long noteId) {
    log.info("Fetching note with id = " + noteId);

    final Note note = noteRepository.findById(noteId)
        .orElseThrow(() -> new NoteNotFoundException(noteId));

    if (note.isDeleted()) {

      throw new NoteNotFoundException(noteId);
    } else {

      return note;
    }
  }

  public Note updatePetNote(long petId, long noteId, Note newData) {
    Note noteFromDb = getPetNote(petId, noteId);
    log.info("Updating of " + noteFromDb.toString() + " to " + newData.toString());

    return getModifiedNote(newData, noteFromDb);
  }

  public Note getPetNote(long petId, long noteId) {
    log.info("Fetching pet's note");
    Note note = getNote(noteId);
    try {
      if (note.getPet().getId() != petId) {

        throw new IllegalArgumentException("Wrong pet ID");
      } else if (note.isDeleted()) {

        throw new NoteNotFoundException(noteId);
      } else {

        return note;
      }

    } catch (NullPointerException error) {

      throw new NullPointerException("Wrong note ID"); // not Pet's note but User's
    }
  }

  public void deleteUserNotes(long userId) {
    AppUser user = getUser(userId);
    if (user.getNotes().isEmpty()) {

      throw new IllegalStateException("No note was found.");
    }
    log.info("Deleting all user's notes");

    final List<Note> userNotes = user.getNotes();
    for (Note note : userNotes) {
      note.delete();
    }

    userRepository.save(user);
  }

  public void deleteUserNote(long userId, long noteId) {
    Note noteFromDb = getUserNote(userId, noteId);
    log.info("Deleting user's note");
    noteFromDb.delete();

    noteRepository.save(noteFromDb);
  }

  public void deletePetNotes(long petId) {
    Pet pet = getPet(petId);
    if (pet.getNotes().isEmpty()) {

      throw new IllegalStateException("No note was found.");
    }
    log.info("Deleting all pet's notes");
    final List<Note> petNotes = pet.getNotes();
    for (Note note : petNotes) {
      note.delete();
    }

    petRepository.save(pet);
  }

  public void deletePetNote(long petId, long noteId) {
    Note noteFromDb = getPetNote(petId, noteId);
    log.info("Deleting pet's note");
    noteFromDb.delete();

    noteRepository.save(noteFromDb);
  }
}
