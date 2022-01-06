package pl.petlovers.Pet2Vet.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.pet.PetService;
import pl.petlovers.Pet2Vet.security.users.AppUserDetails;

import java.util.List;

@RestController
@CrossOrigin
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pets")
    public List<PetDTO> get() {

        return petService.getAll()
                .stream()
                .map(PetDTO::of)
                .toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pets/{petId}")
    public PetDTO get(@PathVariable long petId) {

        return PetDTO.of(petService.get(petId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/pets")
    public PetDTO create(@RequestBody PetDTO petDTO, @AuthenticationPrincipal AppUserDetails user) {


    return petService.create(user.getAppUser().getId(), petDTO);
  }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/pets/{petId}")
    public PetDTO update(@PathVariable long petId, @RequestBody PetDTO petDTO) {

    return PetDTO.of(petService.update(petId, petDTO));
  }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/pets/{petId}")
    public void cancel(@PathVariable long petId) {
        petService.delete(petId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("users/{userId}/pets")
    public List<PetDTO> getAllUserPets(@PathVariable long userId) {
        return petService.getUserPets(userId)
                .stream()
                .map(PetDTO::of)
                .toList();
    }

}
