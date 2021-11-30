package pl.petlovers.Pet2Vet.vaccine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.vaccine.VaccineService;

import java.util.List;

@RestController
@CrossOrigin
public class VaccineController {

  private final VaccineService vaccineService;

  @Autowired
  public VaccineController(VaccineService vaccineService) {
    this.vaccineService = vaccineService;
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("users/{userId}/pets/{petId}/vaccines")
  public List<VaccineDTO> getUserPetsVaccines(
      @PathVariable long userId,
      @PathVariable long petId){

    return vaccineService.getUserPetsVaccines(userId, petId)
        .stream()
        .map(VaccineDTO::of)
        .toList();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("users/{userId}/pets/{petId}/vaccines/{vaccineId}")
  public VaccineDTO getUserPetsVaccine(
      @PathVariable long userId,
      @PathVariable long petId,
      @PathVariable long vaccineId){

    return VaccineDTO.of(vaccineService.getUserPetsVaccine(userId, petId, vaccineId));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("users/{userId}/pets/{petId}/vaccines")
  public VaccineDTO createVaccineInUserPet(
      @PathVariable long userId,
      @PathVariable long petId,
      @RequestBody VaccineDTO vaccineDTO) {

    return VaccineDTO.of(vaccineService.createVaccineInUserPet(userId, petId, vaccineDTO.toVaccine()));
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PutMapping("users/{userId}/pets/{petId}/vaccines/{vaccineId}")
  public VaccineDTO updateUserPetVaccine(
      @PathVariable long userId,
      @PathVariable long petId,
      @PathVariable long vaccineId,
      @RequestBody VaccineDTO vaccineDTO) {

    return VaccineDTO.of(vaccineService.updateUserPetVaccine(userId, petId, vaccineId, vaccineDTO.toVaccine()));
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("users/{userId}/pets/{petId}/vaccines/{vaccineId}")
  public void cancelUserPerVaccine(
      @PathVariable long userId,
      @PathVariable long petId,
      @PathVariable long vaccineId) {

    vaccineService.deleteUserPetVaccine(userId, petId, vaccineId);
  }



  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/vaccines")
  public List<VaccineDTO> get() {
    return vaccineService.getAll()
        .stream()
        .map(VaccineDTO::of)
        .toList();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/vaccines/{vaccineId}")
  public VaccineDTO get(@PathVariable long vaccineId) {
    return VaccineDTO.of(vaccineService.get(vaccineId));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/vaccines")
  public VaccineDTO create(@RequestBody VaccineDTO vaccineDTO) {
    return VaccineDTO.of(vaccineService.create(vaccineDTO.toVaccine()));
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PutMapping("/vaccines/{vaccineId}")
  public VaccineDTO update(@PathVariable long vaccineId, @RequestBody VaccineDTO vaccineDTO) {
    return VaccineDTO.of(vaccineService.update(vaccineId, vaccineDTO.toVaccine()));
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/vaccines/{vaccineId}")
  public void cancel(@PathVariable long vaccineId) {
    vaccineService.delete(vaccineId);
  }

}
