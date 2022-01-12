package pl.petlovers.Pet2Vet.appUser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.petlovers.Pet2Vet.appUser.AppUserService;
import pl.petlovers.Pet2Vet.exceptions.forbidden_exceptions.AppUserForbiddenAccessException;
import pl.petlovers.Pet2Vet.exceptions.forbidden_exceptions.CreatingAdminAccountNotByAdminForbidden;
import pl.petlovers.Pet2Vet.security.users.AppUserDetails;
import pl.petlovers.Pet2Vet.security.users.Roles;

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

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<AppUserDTO> getAll() {

        return appUserService.getAll()
            .stream()
            .map(AppUserDTO::of)
            .toList();
    }

    @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}")
    public AppUserDTO get(@PathVariable long userId, @AuthenticationPrincipal AppUserDetails loggedUser) {

        if (changeOwnAccountOrAdminLogged(userId, loggedUser)) {

            return AppUserDTO.of(appUserService.get(userId));
        } else {
            throw new AppUserForbiddenAccessException();
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AppUserDTO create(@RequestBody AppUserDTO appUserDTO, @RequestHeader String password, @AuthenticationPrincipal AppUserDetails loggedUser) {

        if (appUserDTO.getRole() == Roles.ROLE_ADMIN) {
            if (loggedUser.isAdmin()) {
                return AppUserDTO.of(appUserService.create(appUserDTO, password));
            } else {
                throw new CreatingAdminAccountNotByAdminForbidden();
            }
        }

        return AppUserDTO.of(appUserService.create(appUserDTO, password));
    }

//    todo change login or password
//    todo change role only by admin
    @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{userId}")
    public AppUserDTO update(@PathVariable long userId, @RequestBody AppUserDTO appUserDTO, @AuthenticationPrincipal AppUserDetails loggedUser) {

        if (changeOwnAccountOrAdminLogged(userId, loggedUser)) {

            return AppUserDTO.of(appUserService.update(userId, appUserDTO));
        } else {
            throw new AppUserForbiddenAccessException();
            }
        }

    @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable long userId, @AuthenticationPrincipal AppUserDetails loggedUser) {

        if (changeOwnAccountOrAdminLogged(userId, loggedUser)) {

            appUserService.delete(userId);
        } else {
            throw new AppUserForbiddenAccessException();
        }
    }

    private boolean changeOwnAccountOrAdminLogged(long userToChangeId, AppUserDetails loggedUser) {

        return loggedUser.getId() == userToChangeId || loggedUser.isAdmin();
    }

}
