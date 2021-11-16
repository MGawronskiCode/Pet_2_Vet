package pl.petlovers.Pet2Vet.specie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.specie.PetSpecie;
import pl.petlovers.Pet2Vet.specie.PetSpecieService;

import java.util.List;

@RestController
@RequestMapping("/species")
class SpieceController {

  private final PetSpecieService petSpecieService;

  @Autowired
  public SpieceController(PetSpecieService petSpecieService) {
    this.petSpecieService = petSpecieService;
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public List<PetSpecie> get() {
    return petSpecieService.getAll();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{specieId}")
  public PetSpecie get(@PathVariable long specieId) {
    return petSpecieService.get(specieId);
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PostMapping
  public PetSpecie create(@RequestBody PetSpecie petSpecie) {
    return petSpecieService.create(petSpecie);
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PutMapping
  public PetSpecie update(@PathVariable long specieId, @RequestBody PetSpecie petSpecieNewData) {
    return petSpecieService.update(specieId, petSpecieNewData);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{specieId}")
  public void cancel(@PathVariable long specieId) {
    petSpecieService.delete(specieId);
  }

}
