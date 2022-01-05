package pl.petlovers.Pet2Vet.visit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.pet.PetRepository;

import java.util.List;

@Service
public class VisitService {

  private final VisitRepository visitRepository;
  private final PetRepository petRepository;

  @Autowired
  public VisitService(VisitRepository visitRepository, PetRepository petRepository) {
    this.visitRepository = visitRepository;
    this.petRepository = petRepository;
  }

  public List<Visit> getAll(long petId) {
    return petRepository.getById(petId).getVisits();
  }

  public Visit get(long visitId) {
    return visitRepository.getById(visitId);
  }

  public Visit create(long petId, Visit visit) {
    Pet pet = petRepository.getById(petId);
    pet.addVisit(visit);
    return visitRepository.save(visit);
  }

  public Visit update(long visitId, Visit visit) {
    Visit visitFromDb = get(visitId);
    visitFromDb.modify(visit);
    return visitRepository.save(visitFromDb);
  }

  public void delete(long visitId) {
    visitRepository.delete(get(visitId));
  }
}
