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
import static pl.petlovers.Pet2Vet.entities.appUser.Sex.MALE;
import static pl.petlovers.Pet2Vet.utills.security.users.Roles.ADMIN;
import static pl.petlovers.Pet2Vet.utills.security.users.Roles.PET_OWNER;

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
    loggedAppUser.setRole(PET_OWNER);
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
    loggedAppUser.setRole(ADMIN);
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
    loggedAppUser.setRole(ADMIN);
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
    loggedAppUser.setRole(PET_OWNER);
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
    loggedAppUser.setRole(PET_OWNER);
    final AppUserDetails loggedAppUserDetails = new AppUserDetails(loggedAppUser);

    final AppUser appUserToCreate = new AppUser();
    appUserToCreate.setRole(ADMIN);
    final AppUserDTO userToCreate = AppUserDTO.of(appUserToCreate);
    final String password = "password";

    final AppUserService serviceMock = mock(AppUserService.class);
    final AppUserController controller = new AppUserController(serviceMock);
//    then
    assertThrows(CreatingAdminAccountNotByAdminForbidden.class, () -> controller.create(userToCreate, password, loggedAppUserDetails));
  }

  @Test
  void should_create_correct_account_when_admin_tries_to_create_admin_account(){
    //    given
    final AppUser loggedAppUser = new AppUser();
    loggedAppUser.setRole(ADMIN);
    final AppUserDetails loggedAppUserDetails = new AppUserDetails(loggedAppUser);

    final String password = "password";
    final AppUser appUserToCreate = new AppUser();
    appUserToCreate.setLogin("login");
    appUserToCreate.setRole(ADMIN);
    final AppUserDTO userToCreate = AppUserDTO.of(appUserToCreate);

    final AppUserService serviceMock = mock(AppUserService.class);
    when(serviceMock.create(userToCreate, password)).thenReturn(appUserToCreate);
    final AppUserController controller = new AppUserController(serviceMock);
//    when
    final AppUserDTO appUserDTO = controller.create(userToCreate, password, loggedAppUserDetails);
//    then
    assertEquals("login", appUserDTO.getLogin());
    assertEquals(ADMIN, appUserDTO.getRole());
  }

  @Test
  void should_create_correct_account_when_admin_tries_to_create_non_admin_account(){
    //    given
    final AppUser loggedAppUser = new AppUser();
    loggedAppUser.setRole(ADMIN);
    final AppUserDetails loggedAppUserDetails = new AppUserDetails(loggedAppUser);

    final String password = "password";
    final AppUser appUserToCreate = new AppUser();
    appUserToCreate.setLogin("login");
    appUserToCreate.setRole(PET_OWNER);
    final AppUserDTO userToCreate = AppUserDTO.of(appUserToCreate);

    final AppUserService serviceMock = mock(AppUserService.class);
    when(serviceMock.create(userToCreate, password)).thenReturn(appUserToCreate);
    final AppUserController controller = new AppUserController(serviceMock);
//    when
    final AppUserDTO appUserDTO = controller.create(userToCreate, password, loggedAppUserDetails);
//    then
    assertEquals("login", appUserDTO.getLogin());
    assertEquals(PET_OWNER, appUserDTO.getRole());
  }

  @Test
  void should_create_correct_account_when_non_admin_tries_to_create_non_admin_account(){
    //    given
    final AppUser loggedAppUser = new AppUser();
    loggedAppUser.setRole(PET_OWNER);
    final AppUserDetails loggedAppUserDetails = new AppUserDetails(loggedAppUser);

    final String password = "password";
    final AppUser appUserToCreate = new AppUser();
    appUserToCreate.setLogin("login");
    appUserToCreate.setRole(PET_OWNER);
    final AppUserDTO userToCreate = AppUserDTO.of(appUserToCreate);

    final AppUserService serviceMock = mock(AppUserService.class);
    when(serviceMock.create(userToCreate, password)).thenReturn(appUserToCreate);
    final AppUserController controller = new AppUserController(serviceMock);
//    when
    final AppUserDTO appUserDTO = controller.create(userToCreate, password, loggedAppUserDetails);
//    then
    assertEquals("login", appUserDTO.getLogin());
    assertEquals(PET_OWNER, appUserDTO.getRole());
  }

  @Test
  void should_correctly_update_account_when_trying_to_change_own_account_by_admin() {
//    given
    final AppUser loggedAppUser = new AppUser();
    final long loggedUserId = 2L;
    loggedAppUser.setId(loggedUserId);
    loggedAppUser.setRole(ADMIN);
    final AppUserDetails loggedAppUserDetails = new AppUserDetails(loggedAppUser);

    final AppUser appUserToUpdate = new AppUser("name", MALE, "login", "password", PET_OWNER);
    final long appUserToUpdateId = 2L;
    appUserToUpdate.setId(appUserToUpdateId);
    appUserToUpdate.setRole(PET_OWNER);
    final AppUserDTO userToUpdate = AppUserDTO.of(appUserToUpdate);

    final AppUserService serviceMock = mock(AppUserService.class);
    when(serviceMock.update(appUserToUpdateId, userToUpdate)).thenReturn(appUserToUpdate);
    final AppUserController controller = new AppUserController(serviceMock);
//    when
    final AppUserDTO appUserDTOAfterUpdate = controller.update(appUserToUpdateId, userToUpdate, loggedAppUserDetails);
//    then
    assertEquals(loggedUserId, appUserToUpdateId);
    assertEquals(ADMIN, loggedAppUser.getRole());
    assertEquals("name", appUserDTOAfterUpdate.getName());
    assertEquals(MALE, appUserDTOAfterUpdate.getSex());
    assertEquals("login", appUserDTOAfterUpdate.getLogin());
    assertEquals(PET_OWNER, appUserDTOAfterUpdate.getRole());
  }

  @Test
  void should_correctly_update_account_when_trying_to_change_own_account_by_not_admin() {
    //    given
    final AppUser loggedAppUser = new AppUser();
    final long loggedUserId = 2L;
    loggedAppUser.setId(loggedUserId);
    loggedAppUser.setRole(PET_OWNER);
    final AppUserDetails loggedAppUserDetails = new AppUserDetails(loggedAppUser);

    final AppUser appUserToUpdate = new AppUser("name", MALE, "login", "password", PET_OWNER);
    final long appUserToUpdateId = 2L;
    appUserToUpdate.setId(appUserToUpdateId);
    appUserToUpdate.setRole(PET_OWNER);
    final AppUserDTO userToUpdate = AppUserDTO.of(appUserToUpdate);

    final AppUserService serviceMock = mock(AppUserService.class);
    when(serviceMock.update(appUserToUpdateId, userToUpdate)).thenReturn(appUserToUpdate);
    final AppUserController controller = new AppUserController(serviceMock);
//    when
    final AppUserDTO appUserDTOAfterUpdate = controller.update(appUserToUpdateId, userToUpdate, loggedAppUserDetails);
//    then
    assertEquals(loggedUserId, appUserToUpdateId);
    assertEquals(PET_OWNER, loggedAppUser.getRole());
    assertEquals("name", appUserDTOAfterUpdate.getName());
    assertEquals(MALE, appUserDTOAfterUpdate.getSex());
    assertEquals("login", appUserDTOAfterUpdate.getLogin());
    assertEquals(PET_OWNER, appUserDTOAfterUpdate.getRole());
  }

  @Test
  void should_correctly_update_account_when_trying_to_change_not_own_account_not_admin() {
    //    given
    final AppUser loggedAppUser = new AppUser();
    final long loggedUserId = 1L;
    loggedAppUser.setId(loggedUserId);
    loggedAppUser.setRole(ADMIN);
    final AppUserDetails loggedAppUserDetails = new AppUserDetails(loggedAppUser);

    final AppUser appUserToUpdate = new AppUser("name", MALE, "login", "password", PET_OWNER);
    final long appUserToUpdateId = 2L;
    appUserToUpdate.setId(appUserToUpdateId);
    appUserToUpdate.setRole(PET_OWNER);
    final AppUserDTO userToUpdate = AppUserDTO.of(appUserToUpdate);

    final AppUserService serviceMock = mock(AppUserService.class);
    when(serviceMock.update(appUserToUpdateId, userToUpdate)).thenReturn(appUserToUpdate);
    final AppUserController controller = new AppUserController(serviceMock);
//    when
    final AppUserDTO appUserDTOAfterUpdate = controller.update(appUserToUpdateId, userToUpdate, loggedAppUserDetails);
//    then
    assertNotEquals(loggedUserId, appUserToUpdateId);
    assertEquals(ADMIN, loggedAppUser.getRole());
    assertEquals("name", appUserDTOAfterUpdate.getName());
    assertEquals(MALE, appUserDTOAfterUpdate.getSex());
    assertEquals("login", appUserDTOAfterUpdate.getLogin());
    assertEquals(PET_OWNER, appUserDTOAfterUpdate.getRole());
  }

  @Test
  void should_throw_AppUserForbiddenAccessException_when_trying_to_change_not_own_account_by_not_admin() {
//    given
    final AppUser loggedAppUser = new AppUser();
    final long loggedUserId = 1L;
    loggedAppUser.setId(loggedUserId);
    loggedAppUser.setRole(PET_OWNER);
    final AppUserDetails loggedAppUserDetails = new AppUserDetails(loggedAppUser);

    final AppUser appUserToUpdate = new AppUser();
    final long appUserToUpdateId = 2L;
    appUserToUpdate.setId(appUserToUpdateId);
    appUserToUpdate.setRole(PET_OWNER);
    final AppUserDTO userToUpdate = AppUserDTO.of(appUserToUpdate);

    final AppUserService serviceMock = mock(AppUserService.class);
    final AppUserController controller = new AppUserController(serviceMock);
//    then
    assertNotEquals(loggedUserId, appUserToUpdateId);
    assertNotEquals(ADMIN, loggedAppUser.getRole());
    assertThrows(AppUserForbiddenAccessException.class, () -> controller.update(appUserToUpdateId, userToUpdate, loggedAppUserDetails));
  }

  @Test
  void should_delete_account_when_trying_to_delete_own_account_by_not_admin() {
//    given
    final AppUser loggedAppUser = new AppUser();
    final long loggedUserId = 1L;
    loggedAppUser.setId(loggedUserId);
    loggedAppUser.setRole(PET_OWNER);
    final AppUserDetails loggedAppUserDetails = new AppUserDetails(loggedAppUser);

    final AppUser appUserToDelete = new AppUser();
    final long appUserToDeleteId = 1L;
    appUserToDelete.setId(appUserToDeleteId);
    appUserToDelete.setRole(PET_OWNER);

    final AppUserService serviceMock = mock(AppUserService.class);
    final AppUserController controller = new AppUserController(serviceMock);
//    then
    assertEquals(loggedUserId, appUserToDeleteId);
    assertNotEquals(ADMIN, loggedAppUser.getRole());
    assertDoesNotThrow(() -> controller.delete(appUserToDeleteId, loggedAppUserDetails));
  }

  @Test
  void should_delete_account_when_trying_to_delete_own_account_by_admin() {
//    given
    final AppUser loggedAppUser = new AppUser();
    final long loggedUserId = 1L;
    loggedAppUser.setId(loggedUserId);
    loggedAppUser.setRole(ADMIN);
    final AppUserDetails loggedAppUserDetails = new AppUserDetails(loggedAppUser);

    final AppUser appUserToDelete = new AppUser();
    final long appUserToDeleteId = 1L;
    appUserToDelete.setId(appUserToDeleteId);
    appUserToDelete.setRole(PET_OWNER);

    final AppUserService serviceMock = mock(AppUserService.class);
    final AppUserController controller = new AppUserController(serviceMock);
//    then
    assertEquals(loggedUserId, appUserToDeleteId);
    assertEquals(ADMIN, loggedAppUser.getRole());
    assertDoesNotThrow(() -> controller.delete(appUserToDeleteId, loggedAppUserDetails));
  }

  @Test
  void should_delete_account_when_trying_to_delete_not_own_account_by_admin() {
//    given
    final AppUser loggedAppUser = new AppUser();
    final long loggedUserId = 1L;
    loggedAppUser.setId(loggedUserId);
    loggedAppUser.setRole(ADMIN);
    final AppUserDetails loggedAppUserDetails = new AppUserDetails(loggedAppUser);

    final AppUser appUserToDelete = new AppUser();
    final long appUserToDeleteId = 2L;
    appUserToDelete.setId(appUserToDeleteId);
    appUserToDelete.setRole(PET_OWNER);

    final AppUserService serviceMock = mock(AppUserService.class);
    final AppUserController controller = new AppUserController(serviceMock);
//    then
    assertNotEquals(loggedUserId, appUserToDeleteId);
    assertEquals(ADMIN, loggedAppUser.getRole());
    assertDoesNotThrow(() -> controller.delete(appUserToDeleteId, loggedAppUserDetails));
  }

  @Test
  void should_throw_AppUserForbiddenAccessException_when_trying_to_delete_not_own_account_by_not_admin() {
//    given
    final AppUser loggedAppUser = new AppUser();
    final long loggedUserId = 1L;
    loggedAppUser.setId(loggedUserId);
    loggedAppUser.setRole(PET_OWNER);
    final AppUserDetails loggedAppUserDetails = new AppUserDetails(loggedAppUser);

    final AppUser appUserToDelete = new AppUser();
    final long appUserToDeleteId = 2L;
    appUserToDelete.setId(appUserToDeleteId);
    appUserToDelete.setRole(PET_OWNER);

    final AppUserService serviceMock = mock(AppUserService.class);
    final AppUserController controller = new AppUserController(serviceMock);
//    then
    assertNotEquals(loggedUserId, appUserToDeleteId);
    assertNotEquals(ADMIN, loggedAppUser.getRole());
    assertThrows(AppUserForbiddenAccessException.class, () -> controller.delete(appUserToDeleteId, loggedAppUserDetails));
  }

}
