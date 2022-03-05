package pl.petlovers.Pet2Vet.utills.exceptions.not_found_exceptions;

public class FileNotFoundException extends NotFoundInDatabaseException {

  public FileNotFoundException(Long id) {
    super("Couldn't find file with id = " + id);
  }
}
