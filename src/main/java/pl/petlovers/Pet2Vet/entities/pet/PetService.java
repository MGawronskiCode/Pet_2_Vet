package pl.petlovers.Pet2Vet.entities.pet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.entities.appUser.AppUser;
import pl.petlovers.Pet2Vet.entities.appUser.AppUserService;
import pl.petlovers.Pet2Vet.entities.appUser.controller.AppUserDTO;
import pl.petlovers.Pet2Vet.utills.exceptions.not_found_exceptions.PetNotFoundException;
import pl.petlovers.Pet2Vet.entities.pet.controller.PetDTO;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class PetService {

  private final PetRepository petRepository;
  private final AppUserService appUserService;

  @Autowired
  public PetService(PetRepository petRepository, AppUserService appUserService) {
    this.petRepository = petRepository;
    this.appUserService = appUserService;
  }

  public List<Pet> getAll() {
    log.info("Fetching all pets");

    return petRepository.findAll()
        .stream()
        .filter(pet -> !pet.isDeleted())
        .toList();
  }

  public PetDTO create(PetDTO newPetData) {
    log.info("Creating " + newPetData.toString());
    petRepository.save(newPetData.toPet());

    return newPetData;
  }

  @Transactional
  public PetDTO create(long userId, PetDTO petDTO) {
    log.info("Creating " + petDTO.toString());
    Pet petFromDb = petRepository.save(petDTO.toPet());

    AppUser user = appUserService.get(userId);
    user.addPetToPetsList(petFromDb);
    AppUserDTO appUserDTO = AppUserDTO.of(user);
    appUserService.update(userId, appUserDTO);

    return petDTO;
  }

  public Pet update(long petId, PetDTO petNewData) {
    log.info("Fetching pet with id = " + petId);
    Pet petFromDB = petRepository.getById(petId);
    log.info("Updating of " + petFromDB + " to " + petNewData.toString());
    petFromDB.modify(petNewData.toPet());

    return petRepository.save(petFromDB);
  }

  public void delete(long petId) {
    log.info("Deleting pet with id = " + petId);
    final Pet petFromRepo = get(petId);
    petFromRepo.delete();

    petRepository.save(petFromRepo);
  }

  public Pet get(long petId) {
    log.info("Fetching pet with id = " + petId);
    final Pet pet = petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException(petId));

    if (pet.isDeleted()) {

      throw new PetNotFoundException(petId);
    } else {

      return pet;
    }
  }
}
