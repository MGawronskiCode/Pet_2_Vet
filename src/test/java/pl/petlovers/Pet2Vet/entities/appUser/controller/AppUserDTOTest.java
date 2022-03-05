package pl.petlovers.Pet2Vet.entities.appUser.controller;

import org.junit.jupiter.api.Test;
import pl.petlovers.Pet2Vet.entities.appUser.AppUser;

import static org.junit.jupiter.api.Assertions.*;
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

  @Test
  void should_create_correct_AppUser_when_using_toAppUser_method_on_fully_completed_DTO_with_correct_password() {
//    given
    AppUserDTO userDTO = new AppUserDTO(1L, "name", MALE, "login", ROLE_OWNER);
    String password = "password";
    AppUser user;
//    when
    user = userDTO.toAppUser(password);
//    then
    assertNotNull(user.getPassword());
    assertNotEquals(password, user.getPassword());
    assertEquals(1L, user.getId());
    assertEquals("name", userDTO.getName());
    assertEquals(MALE, user.getSex());
    assertEquals("login", user.getLogin());
    assertEquals(ROLE_OWNER, user.getRole());
  }

  @Test
  void should_create_correct_AppUser_when_using_toAppUser_method_on_partly_completed_DTO_with_correct_password() {
//    given
    AppUserDTO userDTO = new AppUserDTO();
    userDTO.setName("name");
    String password = "password";
    AppUser user;
//    when
    user = userDTO.toAppUser(password);
//    then
    assertNotNull(user.getPassword());
    assertNotEquals(password, user.getPassword());
    assertEquals("name", userDTO.getName());
  }

  @Test
  void should_create_correct_AppUser_when_using_toAppUser_method_on_empty_DTO_with_correct_password() {
//    given
    AppUserDTO userDTO = new AppUserDTO();
    String password = "password";
    AppUser user;
//    when
    user = userDTO.toAppUser(password);
//    then
    assertNotNull(user.getPassword());
    assertNotEquals(password, user.getPassword());
  }

}
