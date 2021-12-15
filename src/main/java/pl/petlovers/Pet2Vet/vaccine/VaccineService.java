package pl.petlovers.Pet2Vet.vaccine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.appUser.AppUserRepository;
import pl.petlovers.Pet2Vet.appUser.controller.AppUserDTO;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.pet.PetNotFoundException;

import java.util.List;
import java.util.Optional;

@Slf4j
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
    log.info("Fetching all vaccines");
    return vaccineRepository.findAll();
  }

  public Vaccine get(long vaccineId) {
    log.info("Fetching vaccine with id = " + vaccineId);
    return vaccineRepository.findById(vaccineId).orElseThrow(() -> new VaccineNotFoundException(vaccineId));
  }

  public Vaccine create(Vaccine vaccine) {
    log.info("Creating " + vaccine.toString());
    vaccineRepository.save(vaccine);

    return vaccine;
  }

  public Vaccine update(long vaccineId, Vaccine vaccineNewData) {
    log.info("Fetching vaccine with id = " + vaccineId);
    Vaccine vaccineFromDB = vaccineRepository.getById(vaccineId);
    log.info("Updating of " + vaccineFromDB + " to " + vaccineNewData.toString());
    vaccineFromDB.modify(vaccineNewData);

    return vaccineRepository.save(vaccineFromDB);
  }

  public void delete(long vaccineId) {
    Vaccine vaccine = get(vaccineId);
    log.info("Deleting vaccine");
    vaccineRepository.delete(vaccine);
  }

  public List<Vaccine> getUserPetsVaccines(long userId, long petId) {
    Optional<Pet> wantedPet = getUserPetById(userId, petId);
    if (wantedPet.isPresent()) {
      log.info("Fetching vaccines");
      return wantedPet.get().getVaccines();
    }

    throw new PetNotFoundException(petId);
  }

  public Vaccine getUserPetsVaccine(long userId, long petId, long vaccineId) {
    Optional<Pet> wantedPet = getUserPetById(userId, petId);
    log.info("Fetching vaccine with id = " + vaccineId);
    if (wantedPet.isPresent()) {
      return wantedPet.get().getVaccines().stream()
          .filter(vaccine -> vaccine.getId() == vaccineId)
          .findFirst()
          .orElseThrow(() -> new VaccineNotFoundException(vaccineId));
    }

    throw new IllegalArgumentException();
  }

  public Vaccine createVaccineInUserPet(long userId, long petId, Vaccine newVaccineData) {
    AppUser user = appUserRepository.getById(userId);
    Optional<Pet> wantedPet = getUserPetById(userId, petId);
    log.info("Creating " + newVaccineData.toString());
    if (wantedPet.isPresent()) {
      Pet tmpPet = wantedPet.get();
      List<Vaccine> tmpPetVaccines = tmpPet.getVaccines();
      addVaccineToPetVaccinesList(newVaccineData, wantedPet, tmpPet, tmpPetVaccines);

      AppUser tmpUser = appUserRepository.getById(userId);
      List<Pet> tmpPets = tmpUser.getPets();
      addPetToUserPetsList(user, tmpPet, tmpUser, tmpPets);

      appUserRepository.save(user);
      return newVaccineData;
    }

    throw new IllegalArgumentException();
  }

  public Vaccine updateUserPetVaccine(long userId, long petId, long vaccineId, Vaccine newVaccineData) {
    Optional<Pet> wantedPet = getUserPetById(userId, petId);
    AppUser wantedUser = appUserRepository.getById(userId);

    if (wantedPet.isPresent()) {
      updateVaccineInPetVaccines(vaccineId, newVaccineData, wantedPet);
      updateUserPetInPetList(petId, wantedUser);

      return newVaccineData;
    }

    throw new IllegalArgumentException();  }

  private void updateVaccineInPetVaccines(long vaccineId, Vaccine newVaccineData, Optional<Pet> wantedPet) {
    Pet tmpPet = wantedPet.get();
    log.info("Fetching vaccine with id = " + vaccineId);
    List<Vaccine> tmpPetVaccines= tmpPet.getVaccines();
    log.info("Updating of " + tmpPetVaccines.toString() + " to " + newVaccineData.toString());
    tmpPetVaccines.removeIf(vaccine -> vaccine.getId() == vaccineId);
    tmpPetVaccines.add(newVaccineData);
    wantedPet.get().modify(tmpPet);
  }

  private void updateUserPetInPetList(long petId, AppUser user) {
    AppUser tmpUser = user;
    tmpUser.getPets().removeIf(pet -> pet.getId().equals(petId));
    user.modify(AppUserDTO.of(tmpUser));
  }


  public void deleteUserPetVaccine(long userId, long petId, long vaccineId) {
    Optional<Pet> wantedPet = getUserPetById(userId, petId);
    AppUser wantedUser = appUserRepository.getById(userId);
    log.info("Deleting vaccine");
    if (wantedPet.isPresent()) {
      deleteVaccineFromPetVaccinesList(vaccineId, wantedPet);
      updateUserPetInPetList(petId, wantedUser);
    }

    throw new IllegalArgumentException();  }

  private void deleteVaccineFromPetVaccinesList(long vaccineId, Optional<Pet> wantedPet) {
    Pet tmpPet = wantedPet.get();
    List<Vaccine> tmpPetVaccines= tmpPet.getVaccines();
    tmpPetVaccines.removeIf(vaccine -> vaccine.getId() == vaccineId);
    wantedPet.get().modify(tmpPet);
  }

  private void addPetToUserPetsList(AppUser user, Pet tmpPet, AppUser tmpUser, List<Pet> tmpPets) {
    tmpPets.add(tmpPet);
    tmpUser.setPets(tmpPets);
    user.modify(AppUserDTO.of(tmpUser));
  }

  private void addVaccineToPetVaccinesList(Vaccine newVaccineData, Optional<Pet> wantedPet, Pet tmpPet, List<Vaccine> tmpPetVaccines) {
    tmpPetVaccines.add(newVaccineData);
    tmpPet.setVaccines(tmpPetVaccines);
    wantedPet.orElseThrow().modify(tmpPet);
  }

  private AppUser getUser(long userId) {
    log.info("Fetching user with id = " + userId);
    return appUserRepository.findById(userId).orElseThrow();
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
