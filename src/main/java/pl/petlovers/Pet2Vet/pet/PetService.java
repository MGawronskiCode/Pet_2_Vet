package pl.petlovers.Pet2Vet.pet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.exceptions.not_found_exceptions.PetNotFoundException;

import java.util.List;

@Slf4j
@Service
public class PetService {

  private final PetRepository petRepository;

  @Autowired
  public PetService(PetRepository petRepository) {
    this.petRepository = petRepository;
  }

  public List<Pet> getAll() {
    log.info("Fetching all pets");
    return petRepository.findAll();
  }

  public Pet create(Pet pet) {
    log.info("Creating " + pet.toString());
    petRepository.save(pet);

    return pet;
  }

  public Pet update(long petId, Pet petNewData) {
    log.info("Fetching pet with id = " + petId);
    Pet petFromDB = petRepository.getById(petId);
    log.info("Updating of " + petFromDB + " to " + petNewData.toString());
    petFromDB.modify(petNewData);

    return petRepository.save(petFromDB);
  }

  public void delete(long petId) {
    petRepository.delete(get(petId));
    log.info("Deleting pet with id = " + petId);
  }

  public Pet get(long petId) {
    log.info("Fetching pet with id = " + petId);
    return petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException(petId));
  }
}
