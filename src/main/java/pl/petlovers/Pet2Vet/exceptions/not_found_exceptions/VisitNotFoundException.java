package pl.petlovers.Pet2Vet.exceptions.not_found_exceptions;

public class VisitNotFoundException extends NotFoundInDatabaseException {

  public VisitNotFoundException(Long id) {
    super("Couldn't find visit with id = " + id);
  }
}
