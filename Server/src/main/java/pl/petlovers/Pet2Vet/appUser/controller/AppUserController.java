package pl.petlovers.Pet2Vet.appUser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.appUser.AppUserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
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
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}")
    public AppUserDTO get(@PathVariable long userId) {
        return AppUserDTO.of(appUserService.get(userId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AppUserDTO create(@RequestBody AppUser appUser) {
        return AppUserDTO.of(appUserService.create(appUser));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{userId}")
    public AppUserDTO update(@PathVariable long userId, @RequestBody AppUser user) {
        return AppUserDTO.of(appUserService.update(userId, user));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable long userId) {
        appUserService.delete(userId);
    }

}
