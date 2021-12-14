package pl.petlovers.Pet2Vet.appUser;

public class AppUserNotFoundException extends RuntimeException{
    public AppUserNotFoundException(Long id) {
        super("Couldn't find user with id = " + id);
    }
}
