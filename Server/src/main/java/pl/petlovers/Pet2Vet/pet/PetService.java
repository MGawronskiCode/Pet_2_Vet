package pl.petlovers.Pet2Vet.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.pet.PetNotFoundException;
import pl.petlovers.Pet2Vet.pet.PetRepository;

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

  public Pet get(long petId){
    return petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException(petId));
  }

  public Pet create(Pet pet){
    petRepository.save(pet);

    return pet;
  }

  public Pet update(long petId, Pet petNewData){
    Pet petFromDB = get(petId);

    petFromDB.setName(petNewData.getName());
    petFromDB.setSex(petNewData.getSex());
    petFromDB.setBirthday(petNewData.getBirthday());
    petFromDB.setSpecie(petNewData.getSpecie());
    petFromDB.setVaccines(petNewData.getVaccines());
    petFromDB.setMeal(petNewData.getMeal());
    petFromDB.setNotes(petNewData.getNotes());

    return petRepository.save(petFromDB);
  }

  public void delete(long petId){
    petRepository.delete(get(petId));
  }
}
