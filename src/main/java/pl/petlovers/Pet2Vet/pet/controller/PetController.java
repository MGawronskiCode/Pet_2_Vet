package pl.petlovers.Pet2Vet.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.exceptions.unautorized_exceptions.PetUnauthorizedAttemptException;
import pl.petlovers.Pet2Vet.pet.PetService;

import java.util.List;

@RestController
@RequestMapping("/pets")
@CrossOrigin
public class PetController {

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

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("users/{userId}/pets/{petId}")
  public PetDTO get(@PathVariable long userId, @PathVariable long petId) {
    if (userHasPetWithId(userId, petId)) {

      return PetDTO.of(petService.get(petId));
    } else {
      throw new PetUnauthorizedAttemptException(userId, petId);
    }
  }





  private boolean userHasPetWithId(long userId, long petId) {
    return true; //todo!!!
  }
}
