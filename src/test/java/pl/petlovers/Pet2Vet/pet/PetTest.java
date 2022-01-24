package pl.petlovers.Pet2Vet.pet;

import org.junit.jupiter.api.Test;
import pl.petlovers.Pet2Vet.Sex;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.meal.Meal;
import pl.petlovers.Pet2Vet.note.Note;
import pl.petlovers.Pet2Vet.visit.Visit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PetTest {

  @Test
  void should_correctly_initialize_pet_when_using_no_arguments_constructor() {
//    given
    Pet pet = new Pet();
//    then
    assertNotNull(pet);

    assertNull(pet.getId());
    assertNull(pet.getName());
    assertNull(pet.getSex());
    assertNull(pet.getBirthday());
    assertNull(pet.getSpecie());
    assertNull(pet.getVaccines());
    assertNull(pet.getMeals());
    assertNull(pet.getNotes());
    assertNull(pet.getAppUsers());
    assertEquals(List.of(), pet.getVisits());

  }

  @Test
  void should_modify_pet_data_when_using_modify_method() {
//    given
    Pet pet = new Pet();
    Pet newPetData = new Pet();
    newPetData.setName("test");
    newPetData.setSex(Sex.MALE);
    newPetData.setBirthday(LocalDate.from(LocalDateTime.of(2000, 1, 1, 12, 0)));
//    when
    pet.modify(newPetData);
//    then
    assertNull(pet.getId());
    assertNull(pet.getSpecie());
    assertNull(pet.getVaccines());
    assertNull(pet.getMeals());
    assertNull(pet.getNotes());
    assertNull(pet.getAppUsers());
    assertEquals(List.of(), pet.getVisits());

    assertEquals("test", newPetData.getName());
    assertEquals(Sex.MALE, newPetData.getSex());
    assertEquals(LocalDate.from(LocalDateTime.of(2000, 1, 1, 12, 0)), newPetData.getBirthday());
  }

  @Test
  void should_add_meal_to_pets_meals_when_using_addMeat_method() {
//    given
    Pet pet = new Pet();
    pet.setMeals(new ArrayList<>());
    Meal meal = new Meal();
//    when
    pet.addMeal(meal);
//    then
    assertNull(pet.getId());
    assertNull(pet.getSpecie());
    assertNull(pet.getVaccines());
    assertNull(pet.getNotes());
    assertNull(pet.getAppUsers());
    assertEquals(List.of(), pet.getVisits());

    assertNotNull(pet.getMeals());
    assertEquals(1, pet.getMeals().size());
    assertEquals(List.of(meal), pet.getMeals());
  }

  @Test
  void should_add_visit_to_pets_visits_when_using_addVisit_method() {
//    given
    Pet pet = new Pet();
    pet.setVisits(new ArrayList<>());
    Visit visit = new Visit();
//    when
    pet.addVisit(visit);
//    then
    assertNotNull(pet.getVisits());
    assertEquals(1, pet.getVisits().size());
    assertEquals(List.of(visit), pet.getVisits());

  }

  @Test
  void should_add_note_when_using_addNote_method() {
//    given
    Pet pet = new Pet();
    pet.setNotes(new ArrayList<>());

    Note note = new Note();
    note.setCreated(LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.of(12, 0)));
    note.setModified(LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.of(12, 0)));
    note.setAppUser(mock(AppUser.class));
    note.setPet(mock(Pet.class));
//    when
    pet.addNote(note);
//    then
    assertNull(pet.getId());
    assertNull(pet.getSpecie());
    assertNull(pet.getVaccines());
    assertNull(pet.getMeals());
    assertNull(pet.getAppUsers());
    assertEquals(List.of(), pet.getVisits());

    assertNotNull(pet.getNotes());
    assertEquals(1, pet.getNotes().size());
    assertEquals(List.of(note), pet.getNotes());
  }

  @Test
  void should_return_correct_string_when_using_toString_method() {
//    given
    Pet pet1 = new Pet();
    Pet pet2 = new Pet();
    pet2.setId(1L);
    pet2.setName("test");
    pet2.setSex(Sex.MALE);
    pet2.setBirthday(LocalDate.of(2000, 1, 1));
//    then
    assertEquals("Pet{id=null, name='null', sex='null', birthday=null}", pet1.toString());
    assertEquals("Pet{id=1, name='test', sex='MALE', birthday=2000-01-01}", pet2.toString());
  }

  @Test
  void should_return_correct_values_when_using_pet_equals_method() {
//    given
    Pet pet1 = new Pet();
    Pet pet2 = new Pet();
    Pet pet3 = new Pet();
    pet3.setId(1L);
//    then
    assertEquals(pet1, pet1);
    assertEquals(pet2, pet2);
    assertEquals(pet1.getId(), pet2.getId());
    assertEquals(pet1.getName(), pet2.getName());
    assertEquals(pet1.getSex(), pet2.getSex());
    assertEquals(pet1.getBirthday(), pet2.getBirthday());
    assertNotEquals(pet1, pet3);
    assertNotEquals(pet3, pet1);
    assertNotEquals(1, pet1);
  }

  @Test
  void should_return_correct_values_when_using_pet_hashCode_method() {
//    given
    Pet pet1 = new Pet();
    Pet pet2 = new Pet();
//    then
    assertNotEquals(pet1.hashCode(), pet2.hashCode());
    assertNotEquals(pet2.hashCode(), pet1.hashCode());
  }

  @Test
  void should_change_isDeleted_flag_on_true_when_using_delete_method() {
//    given
    Pet pet1 = new Pet();
//    when
    pet1.delete();
//    then
    assertTrue(pet1.isDeleted());
  }

  @Test
  void should_change_isDeleted_flag_on_false_when_using_restore_method() {
//    given
    Pet pet1 = new Pet();
//    when
    pet1.delete();
    pet1.restore();
//    then
    assertFalse(pet1.isDeleted());
  }
}
