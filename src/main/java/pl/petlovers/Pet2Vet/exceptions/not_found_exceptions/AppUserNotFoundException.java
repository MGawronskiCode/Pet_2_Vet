package pl.petlovers.Pet2Vet.exceptions.not_found_exceptions;

public class AppUserNotFoundException extends NotFoundInDatabaseException {
    public AppUserNotFoundException(Long id) {
        super("Couldn't find user with id = " + id);
    }
}
