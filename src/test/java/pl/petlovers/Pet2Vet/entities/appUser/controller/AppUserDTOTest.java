package pl.petlovers.Pet2Vet.entities.appUser.controller;

import org.junit.jupiter.api.Test;
import pl.petlovers.Pet2Vet.entities.appUser.AppUser;

import static org.junit.jupiter.api.Assertions.*;
import static pl.petlovers.Pet2Vet.entities.appUser.Sex.MALE;
import static pl.petlovers.Pet2Vet.utills.security.users.Roles.PET_OWNER;

class AppUserDTOTest {

  @Test
  void should_create_correct_DTO_when_using_of_method_with_fully_completed_AppUser() {
//    given
    final AppUser sampleUser = new AppUser("name", MALE, "login", "password", PET_OWNER);
    final AppUserDTO userDTO;
//    when
    userDTO = AppUserDTO.of(sampleUser);
//    then
    assertEquals("name", userDTO.getName());
    assertEquals(MALE, userDTO.getSex());
    assertEquals("login", userDTO.getLogin());
    assertEquals(PET_OWNER, userDTO.getRole());
  }

  @Test
  void should_create_correct_DTO_when_using_of_method_with_partly_completed_AppUser() {
//    given
    final AppUser sampleUser = new AppUser();
    sampleUser.setName("name");
    final AppUserDTO userDTO;
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
    final AppUser sampleUser = new AppUser();
    final AppUserDTO userDTO;
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
    final AppUserDTO userDTO = new AppUserDTO(1L, "name", MALE, "login", PET_OWNER);
    final String password = "password";
    final AppUser user;
//    when
    user = userDTO.toAppUser(password);
//    then
    assertNotNull(user.getPassword());
    assertNotEquals(password, user.getPassword());
    assertEquals(1L, user.getId());
    assertEquals("name", userDTO.getName());
    assertEquals(MALE, user.getSex());
    assertEquals("login", user.getLogin());
    assertEquals(PET_OWNER, user.getRole());
  }

  @Test
  void should_create_correct_AppUser_when_using_toAppUser_method_on_partly_completed_DTO_with_correct_password() {
//    given
    final AppUserDTO userDTO = new AppUserDTO();
    userDTO.setName("name");
    final String password = "password";
    final AppUser user;
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
    final AppUserDTO userDTO = new AppUserDTO();
    final String password = "password";
    final AppUser user;
//    when
    user = userDTO.toAppUser(password);
//    then
    assertNotNull(user.getPassword());
    assertNotEquals(password, user.getPassword());
  }

}
