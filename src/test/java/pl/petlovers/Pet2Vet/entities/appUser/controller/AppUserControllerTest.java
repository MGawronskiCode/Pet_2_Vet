package pl.petlovers.Pet2Vet.entities.appUser.controller;

import org.junit.jupiter.api.Test;
import pl.petlovers.Pet2Vet.entities.appUser.AppUserService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

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


}
