package pl.petlovers.Pet2Vet.vaccine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.appUser.AppUserRepository;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.pet.PetNotFoundException;
import pl.petlovers.Pet2Vet.specie.PetSpecieNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class VaccineService {

  private final AppUserRepository appUserRepository;
  private final VaccineRepository vaccineRepository;

  @Autowired
  public VaccineService(AppUserRepository appUserRepository, VaccineRepository vaccineRepository) {
    this.appUserRepository = appUserRepository;
    this.vaccineRepository = vaccineRepository;
  }

  public List<Vaccine> getAll() {
    return vaccineRepository.findAll();
  }

  public Vaccine get(long vaccineId) {
    return vaccineRepository.findById(vaccineId).orElseThrow(() -> new VaccineNotFoundException(vaccineId));
  }

  public Vaccine create(Vaccine vaccine) {
    vaccineRepository.save(vaccine);

    return vaccine;
  }

  public Vaccine update(long vaccineId, Vaccine vaccineNewData) {
    Vaccine vaccineFromDB = vaccineRepository.getById(vaccineId);
    vaccineFromDB.modify(vaccineNewData);

    return vaccineRepository.save(vaccineFromDB);
  }

  public void delete(long vaccineId) {
    vaccineRepository.delete(get(vaccineId));
  }

  public List<Vaccine> getUserPetsVaccines(long userId, long petId) {
    Optional<Pet> wantedPet = getUserPetById(userId, petId);
    if (wantedPet.isPresent()) {

      return wantedPet.get().getVaccines();
    }

    throw new PetNotFoundException(petId);
  }

  public Vaccine getUserPetsVaccine(long userId, long petId, long vaccineId) {
    Optional<Pet> wantedPet = getUserPetById(userId, petId);
    if (wantedPet.isPresent()) {
      return wantedPet.get().getVaccines().stream()
          .filter(vaccine -> vaccine.getId() == vaccineId)
          .findFirst()
          .orElseThrow();
    }

    throw new PetSpecieNotFoundException(petId);
  }

  public Vaccine createVaccineInUserPet(long userId, String petId, Vaccine toVaccine) {
//    Optional<Pet>

    return null;//todo
  }

  public Vaccine updateUserPetVaccine(long userId, long petId, long vaccineId, Vaccine toVaccine) {
//    Optional<Pet>

    return null;//todo
  }

  public void deleteUserPetVaccine(long userId, long petId, long vaccineId) {
//    Optional<Pet>

//todo
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
