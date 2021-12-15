package pl.petlovers.Pet2Vet.exceptions.not_found_exceptions;

// Exception used to indicate when a note is looked up but not found.
// thrown, this extra tidbit of Spring MVC configuration is used to render an HTTP 404
public class NoteNotFoundException extends NotFoundInDatabaseException {
    public NoteNotFoundException(Long id) {
        super("Couldn't find note with id = " + id);
    }
}
