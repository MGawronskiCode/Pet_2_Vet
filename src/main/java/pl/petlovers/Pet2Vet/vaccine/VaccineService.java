package pl.petlovers.Pet2Vet.vaccine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.petlovers.Pet2Vet.exceptions.not_found_exceptions.PetNotFoundException;
import pl.petlovers.Pet2Vet.exceptions.not_found_exceptions.VaccineNotFoundException;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.pet.PetRepository;
import pl.petlovers.Pet2Vet.vaccine.controller.VaccineDTO;

import java.util.List;

@Slf4j
@Service
public class VaccineService {
  static final String FETCHING_VACCINE = "Fetching vaccine with id = ";
  private final VaccineRepository vaccineRepository;
  private final PetRepository petRepository;

  @Autowired
  public VaccineService(VaccineRepository vaccineRepository, PetRepository petRepository) {
    this.vaccineRepository = vaccineRepository;
    this.petRepository = petRepository;
  }

  public List<Vaccine> getAll() {
    log.info("Fetching all vaccines");

    return vaccineRepository.findAll()
        .stream()
        .filter(vaccine -> !vaccine.isDeleted())
        .toList();
  }

  public Vaccine create(Vaccine vaccine) {
    log.info("Creating " + vaccine.toString());
    vaccineRepository.save(vaccine);

    return vaccine;
  }

  public List<Vaccine> getPetVaccines(long petId) {
    Pet pet = petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException(petId));

    return pet.getVaccines()
        .stream()
        .filter(vaccine -> !vaccine.isDeleted())
        .toList();
  }

  public Vaccine getPetVaccine(long petId, long vaccineId) {
    Pet pet = petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException(petId));
    List<Vaccine> vaccines = pet.getVaccines();

    final Vaccine petVaccine = findVaccineInList(vaccineId, vaccines);

    if (petVaccine.isDeleted()) {
      throw new VaccineNotFoundException(vaccineId);
    } else {

      return petVaccine;
    }
  }

  private Vaccine findVaccineInList(long vaccineId, List<Vaccine> vaccines) {
    for (Vaccine vaccine : vaccines) {
      if (vaccine.getId() == vaccineId) {

        return vaccine;
      }
    }

    throw new VaccineNotFoundException(vaccineId);
  }

  public Vaccine createVaccineInPet(long petId, Vaccine vaccine) {
    Pet petFromRepository = petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException(petId));
    List<Vaccine> petVaccines = petFromRepository.getVaccines();
    petVaccines.add(vaccine);
    petFromRepository.setVaccines(petVaccines);
    petFromRepository.modify(petFromRepository);
    petRepository.save(petFromRepository);

    return vaccine;
  }

  @Transactional
  public Vaccine updatePetVaccine(long petId, long vaccineId, Vaccine vaccineData) {
    Pet petFromRepository = petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException(petId));
    final List<Vaccine> vaccines = petFromRepository.getVaccines();
    updateVaccineIfExistOnList(vaccineId, vaccineData, vaccines);
    petFromRepository.setVaccines(vaccines);

    petRepository.save(petFromRepository);
    update(vaccineId, VaccineDTO.of(vaccineData));

    return vaccineData;
  }

  private void updateVaccineIfExistOnList(long vaccineId, Vaccine vaccine, List<Vaccine> newVaccines) {
    for (int i = 0; i < newVaccines.size(); i++) {
      if (newVaccines.get(i).getId() == vaccineId) {
        vaccine.setId(newVaccines.get(i).getId());
        newVaccines.set(i, vaccine);

        return;
      }
    }

    throw new VaccineNotFoundException(vaccineId);
  }

  public void update(long vaccineId, VaccineDTO vaccineNewData) {
    log.info(FETCHING_VACCINE + vaccineId);
    Vaccine vaccineFromDB = vaccineRepository.getById(vaccineId);
    log.info("Updating of " + vaccineFromDB + " to " + vaccineNewData.toString());
    vaccineFromDB.modify(vaccineNewData);

    vaccineRepository.save(vaccineFromDB);
  }

  public void deletePetVaccine(long petId, long vaccineId) {
    Pet petFromRepository = petRepository.findById(petId).orElseThrow();
    final List<Vaccine> newVaccines = petFromRepository.getVaccines();

    deleteVaccineIfExistOnList(vaccineId, newVaccines);
    petFromRepository.setVaccines(newVaccines);
    petFromRepository.modify(petFromRepository);
    delete(vaccineId);
  }

  private void deleteVaccineIfExistOnList(long vaccineId, List<Vaccine> newVaccines) {
    for (int i = 0; i < newVaccines.size(); i++) {
      if (newVaccines.get(i).getId() == vaccineId) {
        newVaccines.remove(i);

        return;
      }
    }

    throw new VaccineNotFoundException(vaccineId);
  }

  public void delete(long vaccineId) {
    log.info("Deleting vaccine");
    Vaccine vaccine = get(vaccineId);
    vaccine.delete();

    vaccineRepository.save(vaccine);
  }

  public Vaccine get(long vaccineId) {
    log.info(FETCHING_VACCINE + vaccineId);
    final Vaccine vaccine = vaccineRepository.findById(vaccineId).orElseThrow(() -> new VaccineNotFoundException(vaccineId));
    if (vaccine.isDeleted()) {

      throw new VaccineNotFoundException(vaccineId);
    } else {

      return vaccine;
    }
  }
}
