package pl.petlovers.Pet2Vet.entities.visit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.petlovers.Pet2Vet.utills.exceptions.not_found_exceptions.VisitNotFoundException;
import pl.petlovers.Pet2Vet.entities.pet.Pet;
import pl.petlovers.Pet2Vet.entities.pet.PetRepository;

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

    return petRepository.getById(petId).getVisits()
        .stream()
        .filter(visits -> !visits.isDeleted())
        .toList();
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

  public Visit get(long visitId) {
    final Visit visit = visitRepository.getById(visitId);
    if (visit.isDeleted()) {

      throw new VisitNotFoundException(visitId);
    } else {

      return visit;
    }
  }

  public void delete(long visitId) {
    Visit visitFromDb = get(visitId);
    visitFromDb.delete();

    visitRepository.save(visitFromDb);
  }
}
