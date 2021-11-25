package pl.petlovers.Pet2Vet.specie;

import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.appUser.AppUserRepository;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.pet.PetNotFoundException;

import java.util.*;

@Service
public class PetSpecieService {

  private final PetSpecieRepository petSpecieRepository;
  private final AppUserRepository appUserRepository;

  public PetSpecieService(PetSpecieRepository petSpecieRepository, AppUserRepository appUserRepository) {
    this.petSpecieRepository = petSpecieRepository;
    this.appUserRepository = appUserRepository;
  }

  public List<PetSpecie> getAll() {
    return petSpecieRepository.findAll();
  }

  public PetSpecie create(PetSpecie petSpecie) {
    petSpecieRepository.save(petSpecie);
    return petSpecie;
  }

  public PetSpecie update(long specieId, PetSpecie petSpecieNewData) {
    PetSpecie petSpecieFromDB = petSpecieRepository.getById(specieId);
    petSpecieFromDB.modify(petSpecieNewData);

    return petSpecieRepository.save(petSpecieFromDB);
  }

  public List<PetSpecie> getUserPetsSpecies(long userId) {
    List<Pet> userPets = getUsersPets(userId);
    Set<PetSpecie> userPetsSpecies = new HashSet<>();
    for (Pet pet : userPets) {
      userPetsSpecies.add(pet.getSpecie());
    }

    return new ArrayList<>(userPetsSpecies);
  }

  public void delete(long specieId) {
    petSpecieRepository.delete(get(specieId));
  }

  public PetSpecie get(long kindId) {
    return petSpecieRepository.findById(kindId).orElseThrow(() -> new PetSpecieNotFoundException(kindId));
  }

  public PetSpecie getUserPetSpecie(long userId, long petId){
    Optional<Pet> wantedPet = getUserPetById(userId, petId);
    if (wantedPet.isPresent()) {
      return wantedPet
          .get()
          .getSpecie();
    }
    throw new PetSpecieNotFoundException(petId);
  }

  private AppUser getUser(long userId) {
    return appUserRepository.findById(userId).orElseThrow();
  }

  private List<Pet> getUsersPets(long userId) {
    AppUser user = getUser(userId);

    return user.getPets();
  }

  private Optional<Pet> getUserPetById(long userId, long petId){
    List<Pet> userPets = getUsersPets(userId);

    for (Pet pet : userPets) {
      if (pet.getId() == petId) {
        return Optional.of(pet);
      }
    }

    throw new PetNotFoundException(petId);
  }
}
