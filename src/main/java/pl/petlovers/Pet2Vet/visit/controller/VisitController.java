package pl.petlovers.Pet2Vet.visit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.visit.VisitService;
import pl.petlovers.Pet2Vet.visit.controller.VisitDTO;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class VisitController {

  private final VisitService visitService;

  @Autowired
  public VisitController(VisitService visitService) {
    this.visitService = visitService;
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{petId}/visits")
  public List<VisitDTO> getAll(@PathVariable long petId) {
    return visitService.getAll(petId)
      .stream()
      .map(VisitDTO::of)
      .toList();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{petId}/visits/{visitId}")
  public VisitDTO get(@PathVariable long petId,@PathVariable long visitId) {
    return VisitDTO.of(visitService.get(visitId));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/{petId}/visits")
  public VisitDTO create(@PathVariable long petId, @RequestBody VisitDTO visitDTO) {
    return VisitDTO.of(visitService.create(petId, visitDTO.toVisit()));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PutMapping("/{petId}/visits/{visitId}")
  public VisitDTO update(@PathVariable long petId, @PathVariable long visitId, @RequestBody VisitDTO visitDTO) {
    return VisitDTO.of(visitService.update(visitId, visitDTO.toVisit()));
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{petId}/visits/{visitId}")
  public void delete(@PathVariable long petId, @PathVariable long visitId) {
    visitService.delete(visitId);
  }
}
