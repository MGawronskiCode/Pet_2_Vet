package pl.petlovers.Pet2Vet.entities.appUser.controller;

import org.junit.jupiter.api.Test;
import pl.petlovers.Pet2Vet.entities.appUser.AppUser;
import pl.petlovers.Pet2Vet.entities.appUser.AppUserService;
import pl.petlovers.Pet2Vet.utills.exceptions.forbidden_exceptions.AppUserForbiddenAccessException;
import pl.petlovers.Pet2Vet.utills.exceptions.forbidden_exceptions.CreatingAdminAccountNotByAdminForbidden;
import pl.petlovers.Pet2Vet.utills.security.users.AppUserDetails;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.petlovers.Pet2Vet.utills.security.users.Roles.ROLE_ADMIN;
import static pl.petlovers.Pet2Vet.utills.security.users.Roles.ROLE_OWNER;

class AppUserControllerTest {

  @Test
  void should_create_correct_object_when_using_constructor_with_correct_argument() {
//    given
    AppUserController controller;
    AppUserService service = mock(AppUserService.class);
//    when
    controller = new AppUserController(service);
//    then
    assertNotNull(controller);
  }

  @Test
  void should_return_correct_list_when_using_getAll_method1() {
//    given
    final AppUserService service = mock(AppUserService.class);
    when(service.getAll()).thenReturn(getNotEmptySampleAppUserList());
    final AppUserController controller = new AppUserController(service);
//    when
    final List<AppUserDTO> all = controller.getAll();
//    then
    assertNotNull(all);
    assertEquals(3, all.size());
  }

  @Test
  void should_return_correct_list_when_using_getAll_method2() {
//    given
    final AppUserService service = mock(AppUserService.class);
    when(service.getAll()).thenReturn(new ArrayList<>());
    final AppUserController controller = new AppUserController(service);
//    when
    final List<AppUserDTO> all = controller.getAll();
//    then
    assertNotNull(all);
    assertEquals(0, all.size());
  }

  private List<AppUser> getNotEmptySampleAppUserList() {
    final List <AppUser> list = new ArrayList<>();
    list.add(new AppUser());
    list.add(new AppUser());
    list.add(new AppUser());

    return list;
  }

  @Test
  void should_return_correct_DTO_when_using_get_method_with_non_admin_logged_but_request_for_own_account() {
//    given
    final long wantedUserId = 1L;
    final String wantedUserLogin = "login";
    final AppUser wantedUser = new AppUser();
    wantedUser.setId(wantedUserId);
    wantedUser.setLogin(wantedUserLogin);

    final AppUser loggedAppUser = new AppUser();
    loggedAppUser.setId(wantedUserId);
    loggedAppUser.setRole(ROLE_OWNER);
    final AppUserDetails loggedAppUserDetails = new AppUserDetails(loggedAppUser);

    final AppUserService appUserServiceMock = mock(AppUserService.class);
    when(appUserServiceMock.get(wantedUserId)).thenReturn(wantedUser);
    final AppUserController controller = new AppUserController(appUserServiceMock);
//    when
    final AppUserDTO userDTO = controller.get(wantedUserId, loggedAppUserDetails);
//    then
    assertEquals(wantedUserId, userDTO.getId());
    assertEquals(wantedUserLogin, userDTO.getLogin());
  }

  @Test
  void should_return_correct_DTO_when_using_get_method_with_admin_logged_but_request_for_not_own_account() {
//    given
    final long wantedUserId = 1L;
    final String wantedUserLogin = "login";
    final AppUser wantedUser = new AppUser();
    wantedUser.setId(wantedUserId);
    wantedUser.setLogin(wantedUserLogin);

    final AppUser loggedAppUser = new AppUser();
    loggedAppUser.setId(2L);
    loggedAppUser.setRole(ROLE_ADMIN);
    final AppUserDetails loggedAppUserDetails = new AppUserDetails(loggedAppUser);

    final AppUserService appUserServiceMock = mock(AppUserService.class);
    when(appUserServiceMock.get(wantedUserId)).thenReturn(wantedUser);
    final AppUserController controller = new AppUserController(appUserServiceMock);
//    when
    final AppUserDTO userDTO = controller.get(wantedUserId, loggedAppUserDetails);
//    then
    assertEquals(wantedUserId, userDTO.getId());
    assertEquals(wantedUserLogin, userDTO.getLogin());
  }

  @Test
  void should_return_correct_DTO_when_using_get_method_with_admin_logged_and_request_for_his_own_account() {
    //    given
    final long wantedUserId = 1L;
    final String wantedUserLogin = "login";
    final AppUser wantedUser = new AppUser();
    wantedUser.setId(wantedUserId);
    wantedUser.setLogin(wantedUserLogin);

    final AppUser loggedAppUser = new AppUser();
    loggedAppUser.setId(wantedUserId);
    loggedAppUser.setRole(ROLE_ADMIN);
    final AppUserDetails loggedAppUserDetails = new AppUserDetails(loggedAppUser);

    final AppUserService appUserServiceMock = mock(AppUserService.class);
    when(appUserServiceMock.get(wantedUserId)).thenReturn(wantedUser);
    final AppUserController controller = new AppUserController(appUserServiceMock);
//    when
    final AppUserDTO userDTO = controller.get(wantedUserId, loggedAppUserDetails);
//    then
    assertEquals(wantedUserId, userDTO.getId());
    assertEquals(wantedUserLogin, userDTO.getLogin());
  }

  @Test
  void should_throw_AppUserForbiddenException_when_using_get_method_with_non_admin_logged_and_request_for_other_account() {
    //    given
    final long wantedUserId = 1L;
    final String wantedUserLogin = "login";
    final AppUser wantedUser = new AppUser();
    wantedUser.setId(wantedUserId);
    wantedUser.setLogin(wantedUserLogin);

    final AppUser loggedAppUser = new AppUser();
    loggedAppUser.setId(2L);
    loggedAppUser.setRole(ROLE_OWNER);
    final AppUserDetails loggedAppUserDetails = new AppUserDetails(loggedAppUser);

    final AppUserService appUserServiceMock = mock(AppUserService.class);
    final AppUserController controller = new AppUserController(appUserServiceMock);
//    then
    assertThrows(AppUserForbiddenAccessException.class, () -> controller.get(wantedUserId, loggedAppUserDetails));
  }

  @Test
  void should_throw_CreatingAdminAccountNotByAdminForbidden_exception_when_trying_to_create_admin_account_by_non_admin_account() {
//    given
    final AppUser loggedAppUser = new AppUser();
    loggedAppUser.setRole(ROLE_OWNER);
    final AppUserDetails loggedAppUserDetails = new AppUserDetails(loggedAppUser);

    final AppUser appUserToCreate = new AppUser();
    appUserToCreate.setRole(ROLE_ADMIN);
    final AppUserDTO userToCreate = AppUserDTO.of(appUserToCreate);
    final String password = "password";

    final AppUserService serviceMock = mock(AppUserService.class);
    final AppUserController controller = new AppUserController(serviceMock);
//    then
    assertThrows(CreatingAdminAccountNotByAdminForbidden.class, () -> controller.create(userToCreate, password, loggedAppUserDetails));
  }

  @Test
  void tryingToCreateAdminAccount_AdminLogged() {}

  @Test
  void tryingToCreateNonAdminAccount() {}
}
