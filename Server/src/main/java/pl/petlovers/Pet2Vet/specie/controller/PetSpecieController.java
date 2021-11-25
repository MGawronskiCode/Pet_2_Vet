package pl.petlovers.Pet2Vet.specie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.specie.PetSpecie;
import pl.petlovers.Pet2Vet.specie.PetSpecieService;

import java.util.List;

@RestController
public class PetSpecieController {

  private final PetSpecieService petSpecieService;

  @Autowired
  public PetSpecieController(PetSpecieService petService) {
    this.petSpecieService = petService;
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("users/{userId}/pets/species")
  public List<PetSpecieDTO> getUserPetsSpecies(@PathVariable long userId) {
    List<PetSpecie> userPetsSpecies = petSpecieService.getUserPetsSpecies(userId);

    return userPetsSpecies.stream()
        .map(PetSpecieDTO::of)
        .toList();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("users/{userId}/pets/{petId}/species")
  public PetSpecieDTO get(@PathVariable long userId, @PathVariable long petId) {
    return PetSpecieDTO.of(petSpecieService.getUserPetSpecie(userId, petId));
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/species")
  public List<PetSpecieDTO> get() {
    return petSpecieService.getAll()
        .stream()
        .map(PetSpecieDTO::of)
        .toList();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("species/{specieId}")
  public PetSpecieDTO get(@PathVariable long specieId) {
    return PetSpecieDTO.of(petSpecieService.get(specieId));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/species")
  public PetSpecieDTO create(@RequestBody PetSpecieDTO petSpecieDTO) {
    return PetSpecieDTO.of(petSpecieService.create(petSpecieDTO.toPetSpecie()));
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PutMapping("/species/{specieId}")
  public PetSpecieDTO update(@PathVariable long specieId, @RequestBody PetSpecieDTO petSpecieNewDataDTO) {
    return PetSpecieDTO.of(petSpecieService.update(specieId, petSpecieNewDataDTO.toPetSpecie()));
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("species/{specieId}")
  public void cancel(@PathVariable long specieId) {
    petSpecieService.delete(specieId);
  }

}
