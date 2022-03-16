package pl.petlovers.Pet2Vet.utills.exceptions.forbidden_exceptions;

public class ChangeRoleAccessForbiddenException extends ForbiddenAccessException{
  public ChangeRoleAccessForbiddenException() {
    super("Nie masz uprawnień do zmiany roli konta.");
  }
}
