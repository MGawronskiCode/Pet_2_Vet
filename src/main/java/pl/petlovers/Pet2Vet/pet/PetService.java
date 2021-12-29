package pl.petlovers.Pet2Vet.pet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.appUser.AppUserService;
import pl.petlovers.Pet2Vet.appUser.controller.AppUserDTO;
import pl.petlovers.Pet2Vet.exceptions.not_found_exceptions.PetNotFoundException;
import pl.petlovers.Pet2Vet.pet.controller.PetDTO;

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
    return petRepository.findAll();
  }

  public PetDTO create(PetDTO newPetData) {
    log.info("Creating " + newPetData.toString());
    petRepository.save(newPetData.toPet());

    return newPetData;
  }

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
    petRepository.delete(get(petId));
    log.info("Deleting pet with id = " + petId);
  }

  public Pet get(long petId) {
    log.info("Fetching pet with id = " + petId);
    return petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException(petId));
  }

    public List<Pet> getUserPets(long userId) {
      AppUser user = appUserService.get(userId);
      log.info("Fetching all user's pets");
      return user.getPets();
    }
}
