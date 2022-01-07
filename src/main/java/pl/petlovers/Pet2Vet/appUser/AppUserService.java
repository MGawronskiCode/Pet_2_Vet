package pl.petlovers.Pet2Vet.appUser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.petlovers.Pet2Vet.exceptions.not_found_exceptions.AppUserNotFoundException;
import pl.petlovers.Pet2Vet.appUser.controller.AppUserDTO;


import java.util.List;

@Slf4j
@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public List<AppUser> getAll() {
        log.info("Fetching all users");
        return appUserRepository.findAll();
    }

    public AppUser get(long id) {
        log.info("Fetching user with id = " + id);
        return appUserRepository.findById(id)
                .orElseThrow(() -> new AppUserNotFoundException(id));
    }

    public AppUser create(AppUserDTO userDTO, String password) {
        log.info("Creating " + userDTO.toString());
        return appUserRepository.save(userDTO.toAppUser(password));
    }

    public AppUser update(long id, AppUserDTO user){
        AppUser userFromDb = get(id);
        log.info("Updating of " + userFromDb.toString() + " to " + user.toString());
        userFromDb.modify(user);
        return appUserRepository.save(userFromDb);
    }

    public void delete(long id){
        appUserRepository.delete(get(id));
        log.info("Deleting user with id = " + id);
    }

}
