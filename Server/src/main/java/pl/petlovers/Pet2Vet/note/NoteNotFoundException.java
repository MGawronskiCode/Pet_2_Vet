package pl.petlovers.Pet2Vet.note;

// Exception used to indicate when a note is looked up but not found.
// thrown, this extra tidbit of Spring MVC configuration is used to render an HTTP 404
public class NoteNotFoundException extends RuntimeException{
    protected NoteNotFoundException(Long id) {
        super("Couldn't find note with id = " + id);
    }
}
