package pl.petlovers.Pet2Vet.appUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public List<AppUser> getAll() {
        return appUserRepository.findAll();
    }

    public AppUser get(long id) {
        return appUserRepository.findById(id)
                .orElseThrow(() -> new AppUserNotFoundException(id));
    }

    public AppUser create(AppUser user){
        return appUserRepository.save(user);
    }

    public AppUser update(long id, AppUser user){
        AppUser userFromDb = get(id);
        userFromDb.modify(user);
        return appUserRepository.save(userFromDb);
    }

    public void delete(long id){
        appUserRepository.delete(get(id));
    }

}
