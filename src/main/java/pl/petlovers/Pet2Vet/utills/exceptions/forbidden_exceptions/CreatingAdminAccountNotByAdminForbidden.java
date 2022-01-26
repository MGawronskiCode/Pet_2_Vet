package pl.petlovers.Pet2Vet.utills.exceptions.forbidden_exceptions;

public class CreatingAdminAccountNotByAdminForbidden extends ForbiddenAccessException {
  public CreatingAdminAccountNotByAdminForbidden() {
    super("Nie masz uprawnień do tworzenia konta administratorskiego.");
  }
}
