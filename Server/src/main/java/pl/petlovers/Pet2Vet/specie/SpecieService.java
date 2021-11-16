package pl.petlovers.Pet2Vet.specie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class SpecieService {

  private final SpecieRepository specieRepository;

  @Autowired
  public SpecieService(SpecieRepository specieRepository) {
    this.specieRepository = specieRepository;
  }

  public List<Specie> getAll() {
    return specieRepository.findAll();
  }

  public Specie get(long kindId) {
//    return specieRepository.findById(kindId).orElseThrow(() -> new KindNotFoundException(kindId)); todo solve the problem with exception thrown
    return null;
  }

  public Specie create(Specie specie) {
    specieRepository.save(specie);
    return specie;
  }

  public Specie update(long specieId, Specie specieNewData) {
    Specie specieFromDB = get(specieId);

    specieFromDB = specieNewData;

    return (Specie) specieRepository.save(specieFromDB);
  }

  public void delete(long specieId) {
    specieRepository.delete(get(specieId));
  }

}
