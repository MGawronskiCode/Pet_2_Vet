package pl.petlovers.Pet2Vet.vaccine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccineService {

  private final VaccineRepository vaccineRepository;

  @Autowired
  public VaccineService(VaccineRepository vaccineRepository) {
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

}
