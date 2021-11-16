package pl.petlovers.Pet2Vet.vaccine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.vaccine.VaccineService;

import java.util.List;

@RestController
@RequestMapping("/vaccines")
class VaccineController {

  private final VaccineService vaccineService;

  @Autowired
  public VaccineController(VaccineService vaccineService) {
    this.vaccineService = vaccineService;
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public List<VaccineDTO> get() {
    return vaccineService.getAll()
        .stream()
        .map(VaccineDTO::of)
        .toList();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{vaccineId}")
  public VaccineDTO get(@PathVariable long vaccineId) {
    return VaccineDTO.of(vaccineService.get(vaccineId));
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PostMapping()
  public VaccineDTO create(@RequestBody VaccineDTO vaccineDTO) {
    return VaccineDTO.of(vaccineService.create(vaccineDTO.toVaccine()));
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PutMapping("/{vaccineId}")
  public VaccineDTO update(@PathVariable long vaccineId, @RequestBody VaccineDTO vaccineDTO) {
    return VaccineDTO.of(vaccineService.update(vaccineId, vaccineDTO.toVaccine()));
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{vaccineId}")
  public void cancel(@PathVariable long vaccineId) {
    vaccineService.delete(vaccineId);
  }

}
