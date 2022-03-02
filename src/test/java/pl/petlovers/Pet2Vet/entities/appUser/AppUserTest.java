package pl.petlovers.Pet2Vet.entities.appUser;

import org.junit.jupiter.api.Test;
import pl.petlovers.Pet2Vet.entities.Sex;
import pl.petlovers.Pet2Vet.entities.note.Note;
import pl.petlovers.Pet2Vet.utills.security.users.Roles;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class AppUserTest {

  @Test
  void should_create_object_when_using_no_args_constructor() {
//    given
    AppUser user;
//    when
    user = new AppUser();
//    then
    assertNotNull(user);
  }

  @Test
  void should_create_correct_object_when_using_all_args_constructor() {
//    given
    AppUser user;
//    when
    user = getSampleAppUser();
//    then
    assertEquals("name", user.getName());
    assertEquals(Sex.MALE, user.getSex());
    assertEquals("login", user.getLogin());
    assertEquals("password", user.getPassword());
    assertEquals(Roles.ROLE_ADMIN, user.getRole());
  }

  private AppUser getSampleAppUser() {
    return new AppUser(
        "name", Sex.MALE, "login", "password", Roles.ROLE_ADMIN
    );
  }


  @Test
  void should_add_note_to_user_notes_list_when_using_addNote_method_1() {
//    given
    AppUser user = getSampleAppUser();
    user.setNotes(new ArrayList<>());
    Note note = mock(Note.class);
//    when
    user.addNote(note);
//    then
    assertNotNull(user.getNotes());
    assertEquals(1, user.getNotes().size());
  }

  @Test
  void should_add_note_to_user_notes_list_when_using_addNote_method_2() {
//    given
    AppUser user = getSampleAppUser();
    user.setNotes(new ArrayList<>());
    Note note = mock(Note.class);
//    when
    user.addNote(note);
    user.addNote(note);
    user.addNote(note);
//    then
    assertNotNull(user.getNotes());
    assertEquals(3, user.getNotes().size());
  }
}
