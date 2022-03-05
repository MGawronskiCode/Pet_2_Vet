package pl.petlovers.Pet2Vet.entities.appUser.controller;

import org.junit.jupiter.api.Test;
import pl.petlovers.Pet2Vet.entities.appUser.AppUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static pl.petlovers.Pet2Vet.entities.Sex.MALE;
import static pl.petlovers.Pet2Vet.utills.security.users.Roles.ROLE_OWNER;

class AppUserDTOTest {

  @Test
  void should_create_correct_DTO_when_using_of_method_with_fully_completed_AppUser() {
//    given
    AppUser sampleUser = new AppUser("name", MALE, "login", "password", ROLE_OWNER);
    AppUserDTO userDTO;
//    when
    userDTO = AppUserDTO.of(sampleUser);
//    then
    assertEquals("name", userDTO.getName());
    assertEquals(MALE, userDTO.getSex());
    assertEquals("login", userDTO.getLogin());
    assertEquals(ROLE_OWNER, userDTO.getRole());
  }

  @Test
  void should_create_correct_DTO_when_using_of_method_with_partly_completed_AppUser() {
//    given
    AppUser sampleUser = new AppUser();
    sampleUser.setName("name");
    AppUserDTO userDTO;
//    when
    userDTO = AppUserDTO.of(sampleUser);
//    then
    assertEquals("name", userDTO.getName());
    assertNull(userDTO.getSex());
    assertNull(userDTO.getLogin());
    assertNull(userDTO.getRole());
  }

  @Test
  void should_create_correct_DTO_when_using_of_method_with_empty_AppUser() {
//    given
    AppUser sampleUser = new AppUser();
    AppUserDTO userDTO;
//    when
    userDTO = AppUserDTO.of(sampleUser);
//    then
    assertNull(userDTO.getName());
    assertNull(userDTO.getSex());
    assertNull(userDTO.getLogin());
    assertNull(userDTO.getRole());
  }

}
