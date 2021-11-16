package pl.petlovers.Pet2Vet.specie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.specie.Specie;
import pl.petlovers.Pet2Vet.specie.SpecieService;

import java.util.List;

@RestController
@RequestMapping("/species")
class SpieceController {

  private final SpecieService specieService;

  @Autowired
  public SpieceController(SpecieService specieService) {
    this.specieService = specieService;
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public List<Specie> get() {
    return specieService.getAll();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{specieId}")
  public Specie get(@PathVariable long specieId) {
    return specieService.get(specieId);
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PostMapping
  public Specie create(@RequestBody Specie specie) {
    return specieService.create(specie);
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PutMapping
  public Specie update(@PathVariable long specieId, @RequestBody Specie specieNewData) {
    return specieService.update(specieId, specieNewData);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{specieId}")
  public void cancel(@PathVariable long specieId) {
    specieService.delete(specieId);
  }

}
