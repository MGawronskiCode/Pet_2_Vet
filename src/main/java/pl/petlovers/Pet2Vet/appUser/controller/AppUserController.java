package pl.petlovers.Pet2Vet.appUser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.appUser.AppUserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class AppUserController {

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<AppUserDTO> getAll() {
        return appUserService.getAll()
                .stream()
                .map(AppUserDTO::of)
                .toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_ADMIN")
    @GetMapping("/{userId}")
    public AppUserDTO get(@PathVariable long userId) {
        return AppUserDTO.of(appUserService.get(userId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AppUserDTO create(@RequestBody AppUserDTO appUserDTO, @RequestHeader String password) {
        return AppUserDTO.of(appUserService.create(appUserDTO));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{userId}")
    public AppUserDTO update(@PathVariable long userId, @RequestBody AppUserDTO appUserDTO) {
        return AppUserDTO.of(appUserService.update(userId, appUserDTO));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable long userId) {
        appUserService.delete(userId);
    }

}
