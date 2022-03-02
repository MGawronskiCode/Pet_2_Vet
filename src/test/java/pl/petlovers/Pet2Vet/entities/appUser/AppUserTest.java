package pl.petlovers.Pet2Vet.entities.appUser;

import org.junit.jupiter.api.Test;
import pl.petlovers.Pet2Vet.entities.Sex;
import pl.petlovers.Pet2Vet.utills.security.users.Roles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppUserTest {

  @Test
  void should_create_object_when_using_no_args_constructor() {
//    given
    AppUser user;
//    when
    user = new AppUser();
//    then
    assertNotNull(user);
  }

  @Test
  void should_create_correct_object_when_using_all_args_constructor() {
//    given
    AppUser user;
//    when
    user = new AppUser(
        "name", Sex.MALE, "login", "password", Roles.ROLE_ADMIN
    );
//    then
    assertEquals("name", user.getName());
    assertEquals(Sex.MALE, user.getSex());
    assertEquals("login", user.getLogin());
    assertEquals("password", user.getPassword());
    assertEquals(Roles.ROLE_ADMIN, user.getRole());
  }
}
