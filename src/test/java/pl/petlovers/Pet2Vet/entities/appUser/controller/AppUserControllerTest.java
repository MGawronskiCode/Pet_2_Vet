package pl.petlovers.Pet2Vet.entities.appUser.controller;

import org.junit.jupiter.api.Test;
import pl.petlovers.Pet2Vet.entities.appUser.AppUser;
import pl.petlovers.Pet2Vet.entities.appUser.AppUserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    List <AppUser> list = new ArrayList<>();
    list.add(new AppUser());
    list.add(new AppUser());
    list.add(new AppUser());

    return list;
  }


}
