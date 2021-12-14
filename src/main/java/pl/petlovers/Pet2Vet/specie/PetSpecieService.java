package pl.petlovers.Pet2Vet.specie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.appUser.AppUserNotFoundException;
import pl.petlovers.Pet2Vet.appUser.AppUserRepository;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.pet.PetNotFoundException;

import java.util.*;

@Slf4j
@Service
public class PetSpecieService {

  private final PetSpecieRepository petSpecieRepository;
  private final AppUserRepository appUserRepository;

  public PetSpecieService(PetSpecieRepository petSpecieRepository, AppUserRepository appUserRepository) {
    this.petSpecieRepository = petSpecieRepository;
    this.appUserRepository = appUserRepository;
  }

  public List<PetSpecie> getAll() {
    log.info("Fetching all pets species");
    return petSpecieRepository.findAll();
  }

  public PetSpecie create(PetSpecie petSpecie) {
    log.info("Creating " + petSpecie.toString());
    petSpecieRepository.save(petSpecie);
    return petSpecie;
  }

  public PetSpecie update(long specieId, PetSpecie petSpecieNewData) {
    log.info("Fetching pet specie with id = " + specieId);
    PetSpecie petSpecieFromDB = petSpecieRepository.getById(specieId);
    log.info("Updating of " + petSpecieFromDB + " to " + petSpecieNewData.toString());
    petSpecieFromDB.modify(petSpecieNewData);

    return petSpecieRepository.save(petSpecieFromDB);
  }

  public List<PetSpecie> getUserPetsSpecies(long userId) {
    List<Pet> userPets = getUsersPets(userId);
    Set<PetSpecie> userPetsSpecies = new HashSet<>();
    log.info("Fetching pets species");
    for (Pet pet : userPets) {
      userPetsSpecies.add(pet.getSpecie());
    }

    return new ArrayList<>(userPetsSpecies);
  }

  public void delete(long specieId) {
    petSpecieRepository.delete(get(specieId));
    log.info("Deleting pet's specie");
  }

  public PetSpecie get(long kindId) {
    log.info("Fetching pet's specie with id = " + kindId);
    return petSpecieRepository.findById(kindId).orElseThrow(() -> new PetSpecieNotFoundException(kindId));
  }

  public PetSpecie getUserPetSpecie(long userId, long petId){
    Optional<Pet> wantedPet = getUserPetById(userId, petId);
    log.info("Fetching pet's specie");
    if (wantedPet.isPresent()) {
      return wantedPet
          .get()
          .getSpecie();
    }
    throw new PetSpecieNotFoundException(petId);
  }

  private AppUser getUser(long userId) {
    log.info("Fetching user with id = " + userId);
    return appUserRepository.findById(userId).orElseThrow(() -> new AppUserNotFoundException(userId));
  }

  private List<Pet> getUsersPets(long userId) {
    AppUser user = getUser(userId);
    log.info("Fetching user's pets");
    return user.getPets();
  }

  private Optional<Pet> getUserPetById(long userId, long petId){
    List<Pet> userPets = getUsersPets(userId);
    log.info("Fetching pet with id = " + petId);
    for (Pet pet : userPets) {
      if (pet.getId() == petId) {
        return Optional.of(pet);
      }
    }

    throw new PetNotFoundException(petId);
  }
}
