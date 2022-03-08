package pl.petlovers.Pet2Vet.entities.appUser;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
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


  @Test
  void test() {

  }


}
