package pl.petlovers.Pet2Vet.specie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetSpecieService {

  private final PetSpecieRepository petSpecieRepository;

  @Autowired
  public PetSpecieService(PetSpecieRepository petSpecieRepository) {
    this.petSpecieRepository = petSpecieRepository;
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

  public PetSpecie get(long kindId) {
    return petSpecieRepository.findById(kindId).orElseThrow(() -> new PetSpecieNotFoundException(kindId));
  }

  public void delete(long specieId) {
    petSpecieRepository.delete(get(specieId));
  }

}
