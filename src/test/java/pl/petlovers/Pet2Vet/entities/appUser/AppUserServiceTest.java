package pl.petlovers.Pet2Vet.entities.appUser;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import pl.petlovers.Pet2Vet.entities.appUser.controller.AppUserDTO;
import pl.petlovers.Pet2Vet.utills.exceptions.forbidden_exceptions.AppUserWithThisLoginAlreadyExistException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
  void test() {
//    given
    final AppUserDTO userToCreateDTO = new AppUserDTO(1L, "name", MALE, "login", ADMIN);
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
    assertEquals(ADMIN, appUser.getRole());
  }

  @Test
  void should_throw_AppUserWithThisLoginAlreadyExistException_when_trying_to_create_account_with_already_existing_login() {
//    given
    final AppUserDTO userToCreateDTO = new AppUserDTO(1L, "name", MALE, "login", ADMIN);
    final String password = "password";

    final AppUserRepository repository = mock(AppUserRepository.class);
    when(repository.save(any())).thenThrow(DataIntegrityViolationException.class);
    final AppUserService service = new AppUserService(repository);
//    then
    assertThrows(AppUserWithThisLoginAlreadyExistException.class, () -> service.create(userToCreateDTO, password));
  }


  private List<AppUser> getSampleAppUserList() {
    List<AppUser> list = new ArrayList<>();
    AppUser user1 = new AppUser("name1", MALE, "login1", "password1", ADMIN);
    AppUser user2 = new AppUser("name2", MALE, "login2", "password2", PET_OWNER);
    AppUser user3 = new AppUser("name3", FEMALE, "login3", "password3", PET_OWNER);
    AppUser user4 = new AppUser("name4", FEMALE, "login4", "password4", PET_KEEPER);
    AppUser user5 = new AppUser("name5", FEMALE, "login5", "password5", VET);
    user5.delete();

    list.add(user1);
    list.add(user2);
    list.add(user3);
    list.add(user4);
    list.add(user5);

    return list;
  }



}
