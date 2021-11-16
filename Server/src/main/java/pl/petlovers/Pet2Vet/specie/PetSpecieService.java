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
    PetSpecie petSpecieFromDB = get(specieId);

    petSpecieFromDB = petSpecieNewData;

    return (PetSpecie) petSpecieRepository.save(petSpecieFromDB);
  }

  //  @Enumerated(EnumType.ORDINAL)
  public PetSpecie get(long kindId) {
//    return petSpecieRepository.findById(kindId).orElseThrow(() -> new PetSpecieNotFoundException(kindId)); //todo solve the problem with exception thrown
    return null;
  }

  public void delete(long specieId) {
    petSpecieRepository.delete(get(specieId));
  }

}
