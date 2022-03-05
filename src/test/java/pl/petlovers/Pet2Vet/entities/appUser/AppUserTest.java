package pl.petlovers.Pet2Vet.entities.appUser;

import org.junit.jupiter.api.Test;
import pl.petlovers.Pet2Vet.entities.Sex;
import pl.petlovers.Pet2Vet.entities.appUser.controller.AppUserDTO;
import pl.petlovers.Pet2Vet.entities.note.Note;
import pl.petlovers.Pet2Vet.entities.pet.Pet;
import pl.petlovers.Pet2Vet.utills.security.users.Roles;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AppUserTest {

  @Test
  void should_create_object_when_using_no_args_constructor() {
//    given
    final AppUser user;
//    when
    user = new AppUser();
//    then
    assertNotNull(user);
  }

  @Test
  void should_create_correct_object_when_using_all_args_constructor() {
//    given
    final AppUser user;
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
    final AppUser user = getSampleAppUser();
    user.setNotes(new ArrayList<>());
    final Note note = mock(Note.class);
//    when
    user.addNote(note);
//    then
    assertNotNull(user.getNotes());
    assertEquals(1, user.getNotes().size());
  }

  @Test
  void when_using_modify_called_with_dto_with_blank_name_and_null_sex_should_not_change_the_name() {
//    given
    final AppUser user = getSampleAppUser();
    final AppUserDTO dto = mock(AppUserDTO.class);
//    when
    user.modify(dto);
//    then
    assertNull(dto.getSex());
    assertEquals("name", user.getName());
  }

  @Test
  void when_using_modify_called_with_dto_with_null_sex_and_blank_name_should_not_change_the_name() {
//    given
    final AppUser user = getSampleAppUser();
    final AppUserDTO dto = mock(AppUserDTO.class);
//    when
    user.modify(dto);
//    then
    assertNull(dto.getSex());
    assertEquals(Sex.MALE, user.getSex());
  }

  @Test
  void when_using_modify_called_with_dto_with_non_blank_name_and_null_sex_should_not_change_the_sex() {
//    given
    final AppUser user = getSampleAppUser();
    final AppUserDTO dto = mock(AppUserDTO.class);
    dto.setName("name");
//    when
    user.modify(dto);
//    then
    assertNull(dto.getSex());
    assertEquals(Sex.MALE, user.getSex());
  }

  @Test
  void when_using_modify_called_with_dto_with_blank_name_and_not_null_sex_should_not_change_the_name() {
//    given
    final AppUser user = getSampleAppUser();
    final AppUserDTO dto = mock(AppUserDTO.class);
    dto.setSex(Sex.MALE);
//    when
    user.modify(dto);
//    then
    assertNull(dto.getName());
    assertEquals("name", user.getName());
  }

  @Test
  void when_using_modify_called_with_dto_with_changed_correct_name_and_null_sex_should_change_the_name_but_not_the_sex() {
//    given
    final AppUser user = getSampleAppUser();
    final AppUserDTO dto = new AppUserDTO();
    dto.setName("changed name");
//    when
    user.modify(dto);
//    then
    assertNull(dto.getSex());
    assertEquals("changed name", user.getName());
  }

  @Test
  void when_using_modify_called_with_dto_with_changed_null_name_and_changed_correct_sex_should_change_the_sex_but_not_the_name() {
//    given
    final AppUser user = getSampleAppUser();
    final AppUserDTO dto = new AppUserDTO();
    dto.setSex(Sex.FEMALE);
//    when
    user.modify(dto);
//    then
    assertNull(dto.getName());
    assertEquals(Sex.FEMALE, user.getSex());
  }

  @Test
  void when_using_modify_called_with_dto_with_correct_changed_name_and_sex_should_change_the_sex_the_name() {
//    given
    final AppUser user = getSampleAppUser();
    final AppUserDTO dto = new AppUserDTO();
    dto.setName("changed name");
    dto.setSex(Sex.MALE);
//    when
    user.modify(dto);
//    then
    assertEquals("changed name", user.getName());
    assertEquals(Sex.MALE, user.getSex());
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

  @Test
  void should_add_pet_to_user_pets_list_when_using_addPet_method_1() {
//    given
    AppUser user = getSampleAppUser();
    user.setPets(new ArrayList<>());
    Pet pet = mock(Pet.class);
//    when
    user.addPetToPetsList(pet);
//    then
    assertNotNull(user.getPets());
    assertEquals(1, user.getPets().size());
  }

  @Test
  void should_add_pet_to_user_pets_list_when_using_addPet_method_3() {
//    given
    AppUser user = getSampleAppUser();
    user.setPets(new ArrayList<>());
    Pet pet = mock(Pet.class);
//    when
    user.addPetToPetsList(pet);
    user.addPetToPetsList(pet);
    user.addPetToPetsList(pet);
//    then
    assertNotNull(user.getPets());
    assertEquals(3, user.getPets().size());
  }

  @Test
  void isDeleted_should_return_false_when_user_not_deleted() {
//    given
    AppUser user = getSampleAppUser();
    boolean userDeleted;
//    when
    userDeleted = user.isDeleted();
//    then
    assertFalse(userDeleted);
  }

  @Test
  void isDeleted_should_return_true_when_user_deleted() {
//    given
    AppUser user = getSampleAppUser();
    boolean userDeleted;
//    when
    user.delete();
    userDeleted = user.isDeleted();
//    then
    assertTrue(userDeleted);
  }

  @Test
  void restore_should_not_do_any_changes_on_non_deleted_user() {
//    given
    AppUser user = getSampleAppUser();
    boolean userDeleted;
//    when
    user.restore();
    userDeleted = user.isDeleted();
//    then
    assertFalse(userDeleted);
  }

  @Test
  void isDeleted_should_return_false_when_user_restored() {
//    given
    AppUser user = getSampleAppUser();
    boolean userDeleted;
//    when
    user.delete();
    user.restore();
    userDeleted = user.isDeleted();
//    then
    assertFalse(userDeleted);
  }

}
