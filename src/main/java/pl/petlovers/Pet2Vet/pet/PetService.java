package pl.petlovers.Pet2Vet.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

  private final PetRepository petRepository;

  @Autowired
  public PetService(PetRepository petRepository) {
    this.petRepository = petRepository;
  }

  public List<Pet> getAll() {
    return petRepository.findAll();
  }

  public Pet create(Pet pet) {
    petRepository.save(pet);

    return pet;
  }

  public Pet update(long petId, Pet petNewData) {
    Pet petFromDB = petRepository.getById(petId);
    petFromDB.modify(petNewData);

    return petRepository.save(petFromDB);
  }

  public void delete(long petId) {
    petRepository.delete(get(petId));
  }

  public Pet get(long petId) {
    return petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException(petId));
  }
}
