package pl.petlovers.Pet2Vet.utills;

import pl.petlovers.Pet2Vet.utills.exceptions.PasswordNonValidPatternException;

public class PasswordValidator {


  public static boolean validPassword(String password) {
    if (password == null) {
      throw new PasswordNonValidPatternException();
    }

    if (password.length() < 5) {

    }
  }
}
