package pl.petlovers.Pet2Vet.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.pet.PetService;

import java.util.List;

@RestController
@RequestMapping("/pets")
class PetController {

  private final PetService petService;

  @Autowired
  public PetController(PetService petService) {
    this.petService = petService;
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public List<PetDTO> get() {
    return petService.getAll()
        .stream()
        .map(PetDTO::of)
        .toList();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{petId}")
  public PetDTO get(@PathVariable long petId) {
    return PetDTO.of(petService.get(petId));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public PetDTO create(@RequestBody PetDTO petDTO) {
    return PetDTO.of(petService.create(petDTO.toPet()));
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PutMapping("/{petId}")
  public PetDTO update(@PathVariable long petId, @RequestBody PetDTO petDTO) {
    return PetDTO.of(petService.update(petId, petDTO.toPet()));
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{petId}")
  public void cancel(@PathVariable long petId) {
    petService.delete(petId);
  }
}
