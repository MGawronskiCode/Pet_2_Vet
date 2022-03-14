package pl.petlovers.Pet2Vet.utills;

import pl.petlovers.Pet2Vet.utills.exceptions.PasswordNonValidPatternException;

import static java.lang.Character.isDigit;

public class PasswordValidator {

  private PasswordValidator() {}

  public static boolean validPassword(String password) {

    if ((notNull(password) && sufficientlyLongPassword(password) && containsDigit(password))) {
      return true;
    }

    throw new PasswordNonValidPatternException();
  }

  private static boolean notNull(String password) {

    return password != null;
  }

  private static boolean sufficientlyLongPassword(String password) {
    final int MINIMUM_PASSWORD_LENGTH = 5;

    return password.length() >= MINIMUM_PASSWORD_LENGTH;
  }

  private static boolean containsDigit(String password) {
    for (int i = 0; i < password.length(); i++) {
      if (isDigit(password.charAt(i))) {
        return true;
      }
    }

    return false;
  }
}
