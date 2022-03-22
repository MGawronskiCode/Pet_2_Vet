package pl.petlovers.Pet2Vet.entities.appUser;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import pl.petlovers.Pet2Vet.entities.appUser.controller.AppUserDTO;
import pl.petlovers.Pet2Vet.utills.exceptions.forbidden_exceptions.AppUserWithThisLoginAlreadyExistException;
import pl.petlovers.Pet2Vet.utills.exceptions.not_found_exceptions.AppUserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.petlovers.Pet2Vet.entities.appUser.Sex.FEMALE;
import static pl.petlovers.Pet2Vet.entities.appUser.Sex.MALE;
import static pl.petlovers.Pet2Vet.utills.security.users.Roles.*;

class AppUserServiceTest {

  @Test
  void should_create_correct_service_object_when_using_constructor_with_correct_argument() {
//    given
    final AppUserRepository repository = mock(AppUserRepository.class);
    final AppUserService service;
//    when
    service = new AppUserService(repository);
//    then
    assertNotNull(service);
  }


  @Test
  void should_return_correct_list_when_using_getAll_method() {
//    given
    final AppUserRepository repository = mock(AppUserRepository.class);
    when(repository.findAll()).thenReturn(getSampleAppUserList());
    final AppUserService service = new AppUserService(repository);
//    when
    final List<AppUser> allUsers = service.getAll();
//    then
    assertEquals(4, allUsers.size());
    assertEquals("name1", allUsers.get(0).getName());
    assertEquals("name2", allUsers.get(1).getName());
    assertEquals("name3", allUsers.get(2).getName());
    assertEquals("name4", allUsers.get(3).getName());
  }

  @Test
  void should_create_correct_account_if_its_login_doesnt_exist_in_db_when_using_create_method() {
//    given
    final AppUserDTO userToCreateDTO = new AppUserDTO(1L, "name", MALE, "login", ROLE_ADMIN);
    final String password = "password";

    final AppUserRepository repository = mock(AppUserRepository.class);
    when(repository.save(any())).thenReturn(userToCreateDTO.toAppUser(password));
    final AppUserService service = new AppUserService(repository);
//    when
    final AppUser appUser = service.create(userToCreateDTO, password);
//    then
    assertEquals(1L, appUser.getId());
    assertEquals("name", appUser.getName());
    assertEquals(MALE, appUser.getSex());
    assertEquals("login", appUser.getLogin());
    assertEquals(ROLE_ADMIN, appUser.getRole());
  }

  @Test
  void should_throw_AppUserWithThisLoginAlreadyExistException_when_trying_to_create_account_with_already_existing_login() {
//    given
    final AppUserDTO userToCreateDTO = new AppUserDTO(1L, "name", MALE, "login", ROLE_ADMIN);
    final String password = "password";

    final AppUserRepository repository = mock(AppUserRepository.class);
    when(repository.save(any())).thenThrow(DataIntegrityViolationException.class);
    final AppUserService service = new AppUserService(repository);
//    then
    assertThrows(AppUserWithThisLoginAlreadyExistException.class, () -> service.create(userToCreateDTO, password));
  }

  private List<AppUser> getSampleAppUserList() {
    List<AppUser> list = new ArrayList<>();
    AppUser user1 = new AppUser("name1", MALE, "login1", "password1", ROLE_ADMIN);
    AppUser user2 = new AppUser("name2", MALE, "login2", "password2", ROLE_OWNER);
    AppUser user3 = new AppUser("name3", FEMALE, "login3", "password3", ROLE_OWNER);
    AppUser user4 = new AppUser("name4", FEMALE, "login4", "password4", ROLE_KEEPER);
    AppUser user5 = new AppUser("name5", FEMALE, "login5", "password5", ROLE_VET);
    user5.delete();

    list.add(user1);
    list.add(user2);
    list.add(user3);
    list.add(user4);
    list.add(user5);

    return list;
  }

  @Test
  void should_update_account_when_using_update_method_correctly() {
//    given
    final AppUserDTO userToUpdateDTO = new AppUserDTO(2L, "name2", FEMALE, "login", ROLE_ADMIN);
    final String password = "password";
    final AppUserDTO newUserData = new AppUserDTO(1L, "name", MALE, "login", ROLE_ADMIN);

    final AppUserRepository repository = mock(AppUserRepository.class);
    when(repository.save(any())).thenReturn(newUserData.toAppUser(password));
    when(repository.findById(anyLong())).thenReturn(Optional.of(userToUpdateDTO.toAppUser(password)));
    final AppUserService service = new AppUserService(repository);
//    when
    final AppUser userAfterUpdate = service.update(2L, newUserData);
//    then
    assertEquals(newUserData.getName(), userAfterUpdate.getName());
    assertEquals(newUserData.getSex(), userAfterUpdate.getSex());
    assertEquals(newUserData.getRole(), userAfterUpdate.getRole());
  }

  @Test
  void should_throw_AppUserNotFoundException_when_trying_to_update_non_existing_user() {
//    given
    final AppUserDTO newUserData = new AppUserDTO(1L, "name", MALE, "login", ROLE_ADMIN);

    final AppUserRepository repository = mock(AppUserRepository.class);
    when(repository.findById(anyLong())).thenThrow(AppUserNotFoundException.class);
    final AppUserService service = new AppUserService(repository);
//    then
    assertThrows(AppUserNotFoundException.class, () -> service.update(2L, newUserData));
  }

//  todo update password tests - how to test password (new similar class without bcrypt?)
  @Test
  void shouldCorrectlyChangePasswordWhenUsingUpdatePasswordMethod() {
//    given
    final String oldPassword = "oldPassword1";
    final String newPassword = "newPassword1";
    final AppUser userToUpdate = new AppUser("name", Sex.MALE, "login", oldPassword, ROLE_OWNER);
    final long userToUpdateId = 1L;
    userToUpdate.setId(userToUpdateId);

    final AppUserRepository repository = mock(AppUserRepository.class);
    when(repository.findById(userToUpdateId)).thenReturn(Optional.of(userToUpdate));
    when(repository.save(userToUpdate)).thenReturn(userToUpdate);
    final AppUserService service = new AppUserService(repository);
//    when
    final AppUser appUserAfterUpdate = service.updatePassword(userToUpdateId, newPassword);
//    then
    assertEquals(newPassword, appUserAfterUpdate.getPassword());
  }

@Test
void shouldCorrectlyChangeLoginWhenUsingUpdateLoginMethod() {
//    given
  final String oldLogin = "oldLogin";
  final String newLogin = "newLogin";
  final AppUser userToUpdate = new AppUser("name", Sex.MALE, oldLogin, "password", ROLE_OWNER);
  final long userToUpdateId = 1L;
  userToUpdate.setId(userToUpdateId);

  final AppUserRepository repository = mock(AppUserRepository.class);
  when(repository.findById(userToUpdateId)).thenReturn(Optional.of(userToUpdate));
  when(repository.save(userToUpdate)).thenReturn(userToUpdate);
  final AppUserService service = new AppUserService(repository);
//    when
  final AppUser appUserAfterUpdate = service.updateLogin(userToUpdateId, newLogin);
//    then
  assertEquals(newLogin, appUserAfterUpdate.getLogin());
}

//  todo delete tests
@Test
void shouldCorrectlyDeleteUserWhenUsingDeleteMethod() {
//    given
  final AppUser userToUpdate = new AppUser("name", Sex.MALE, "login", "password", ROLE_OWNER);

  final AppUserRepository repository = mock(AppUserRepository.class);
  when(repository.findById(anyLong())).thenReturn(Optional.of(userToUpdate));
  when(repository.save(userToUpdate)).thenReturn(userToUpdate);
  final AppUserService service = new AppUserService(repository);
//    when
  service.delete(1L);
//    then
  assertTrue(userToUpdate.isDeleted());
}
}
