package pl.petlovers.Pet2Vet.visit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.exceptions.not_found_exceptions.VisitNotFoundException;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.pet.PetService;

import java.util.List;

@Service
public class VisitService {

  private final VisitRepository visitRepository;
  private final PetService petService;

  @Autowired
  public VisitService(VisitRepository visitRepository, PetService petService) {
    this.visitRepository = visitRepository;
    this.petService = petService;
  }

  public List<Visit> getAll(long petId) {
    Pet pet = petService.get(petId);
    return pet.getVisits();
  }

  public Visit get(long visitId) {
    return visitRepository.findById(visitId)
            .orElseThrow(() -> new VisitNotFoundException(visitId));
  }

  public Visit create(long petId, Visit visit) {
    Pet pet = petService.get(petId);
    visit.setPet(pet);
    pet.addVisit(visit);
    visitRepository.save(visit);
    return visit;
  }

  public Visit update(long visitId, Visit visit) {
    Visit visitFromDb = get(visitId);
    visitFromDb.modify(visit);
    return visitRepository.save(visitFromDb);
  }

  public void delete(long petId, long visitId) {
    Visit visit = get(visitId);
    visitRepository.delete(visit);
  }
}
