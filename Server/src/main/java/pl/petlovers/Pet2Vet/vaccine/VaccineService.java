package pl.petlovers.Pet2Vet.vaccine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.appUser.AppUserRepository;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.pet.PetNotFoundException;

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

    throw new IllegalArgumentException();
  }

  public Vaccine createVaccineInUserPet(long userId, long petId, Vaccine newVaccineData) {
    AppUser user = appUserRepository.getById(userId);
    Optional<Pet> wantedPet = getUserPetById(userId, petId);
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
    List<Vaccine> TmpPetVaccines= tmpPet.getVaccines();
    TmpPetVaccines.removeIf(vaccine -> vaccine.getId() == vaccineId);
    TmpPetVaccines.add(newVaccineData);
    wantedPet.get().modify(tmpPet);
  }

  private void updateUserPetInPetList(long petId, AppUser user) {
    AppUser tmpUser = user;
    tmpUser.getPets().removeIf(pet -> pet.getId().equals(petId));
    user.modify(tmpUser);
  }


  public void deleteUserPetVaccine(long userId, long petId, long vaccineId) {
//    Optional<Pet> wantedPet = getUserPetById(userId, petId);todo
//if (wantedPet.isPresent()) {}
    throw new IllegalArgumentException();  }

  private void addPetToUserPetsList(AppUser user, Pet tmpPet, AppUser tmpUser, List<Pet> tmpPets) {
    tmpPets.add(tmpPet);
    tmpUser.setPets(tmpPets);
    user.modify(tmpUser);
  }

  private void addVaccineToPetVaccinesList(Vaccine newVaccineData, Optional<Pet> wantedPet, Pet tmpPet, List<Vaccine> tmpPetVaccines) {
    tmpPetVaccines.add(newVaccineData);
    tmpPet.setVaccines(tmpPetVaccines);
    wantedPet.orElseThrow().modify(tmpPet);
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
