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

  public Vaccine create(Vaccine vaccine) {
    vaccineRepository.save(vaccine);
    return vaccine;
  }

  public Vaccine update(long vaccineId, Vaccine vaccineNewData) {
    Vaccine vaccineFromDB = get(vaccineId);

    vaccineFromDB.setName(vaccineNewData.getName());
//    vaccineFromDB.setPet(vaccineNewData.getPet());
    vaccineFromDB.setDateTime(vaccineNewData.getDateTime());

    return vaccineRepository.save(vaccineFromDB);
  }

  public Vaccine get(long vaccineId) {
    return vaccineRepository.findById(vaccineId).orElseThrow(() -> new VaccineNotFoundException(vaccineId));
  }

  public void delete(long vaccineId) {
    vaccineRepository.delete(get(vaccineId));
  }

}
