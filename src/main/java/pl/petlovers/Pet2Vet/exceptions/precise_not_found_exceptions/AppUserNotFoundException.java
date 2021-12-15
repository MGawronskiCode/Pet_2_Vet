package pl.petlovers.Pet2Vet.exceptions.precise_not_found_exceptions;

import pl.petlovers.Pet2Vet.exceptions.NotFoundInDatabaseException;

public class AppUserNotFoundException extends NotFoundInDatabaseException {
    public AppUserNotFoundException(Long id) {
        super("Couldn't find user with id = " + id);
    }
}
