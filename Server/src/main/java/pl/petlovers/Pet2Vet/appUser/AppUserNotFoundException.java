package pl.petlovers.Pet2Vet.appUser;

// Exception used to indicate when a note is looked up but not found.
// thrown, this extra tidbit of Spring MVC configuration is used to render an HTTP 404
public class AppUserNotFoundException extends RuntimeException{
    public AppUserNotFoundException(Long id) {
        super("Couldn't find user with id = " + id);
    }
}
