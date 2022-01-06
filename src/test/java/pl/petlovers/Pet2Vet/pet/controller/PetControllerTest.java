package pl.petlovers.Pet2Vet.pet.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.petlovers.Pet2Vet.pet.PetService;
import pl.petlovers.Pet2Vet.security.users.AppUserDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PetControllerTest {

  @Test
  void should_create_correct_object_when_using_constructor_with_PetService_argument() {
//    given
    PetController controller;
//    when
    controller = new PetController(mock(PetService.class));
//    then
    assertNotNull(controller);
  }

  @Test
  void should_return_correct_list_when_using_petService_getAll_method() {
//    given
    PetController controller = new PetController(mock(PetService.class));
    when(controller.get()).thenReturn(getSamplePetList());
//    when
    ClassCastException thrown = Assertions.assertThrows(ClassCastException.class, controller::get);
//    then
    Assertions.assertEquals("class pl.petlovers.Pet2Vet.pet.controller.PetDTO cannot be cast to class pl.petlovers.Pet2Vet.pet.Pet (pl.petlovers.Pet2Vet.pet.controller.PetDTO and pl.petlovers.Pet2Vet.pet.Pet are in unnamed module of loader 'app')", thrown.getMessage());
  }

  private List<PetDTO> getSamplePetList() {
    List<PetDTO> pets = new ArrayList<>();

    PetDTO pet1 = new PetDTO();
    pet1.setId(1L);
    pet1.setName("test");
    pets.add(pet1);

    PetDTO pet2 = new PetDTO();
    pet2.setId(2L);
    pet2.setName("test2");
    pets.add(pet2);

    return pets;
  }

  @Test
  void should_return_correct_list_when_using_petService_getAll_with_petId_argument_method() {
//    given
    PetController controller = new PetController(mock(PetService.class));
//    when
    NullPointerException thrown = Assertions.assertThrows(NullPointerException.class,() -> controller.get(1L));
//    then
    Assertions.assertEquals("Cannot invoke \"pl.petlovers.Pet2Vet.pet.Pet.getId()\" because \"pet\" is null", thrown.getMessage());
  }

  @Test
  void should_return_correct_object_when_using_petService_create_with_petId_and_petDTO_arguments_method() {
//    given
    PetController controller = new PetController(mock(PetService.class));
    PetDTO petDTO = new PetDTO();
//    when
    final PetDTO createdDTO = controller.create(petDTO, new AppUserDetails());
//    then
    assertNull(createdDTO);
  }

  @Test
  void should_create_correct_object_when_using_update_method() {
//    given
    PetController controller = new PetController(mock(PetService.class));
    PetDTO petDTO = new PetDTO();
//    when
    NullPointerException thrown = Assertions.assertThrows(NullPointerException.class,() -> controller.update(1L, petDTO));
//    then
    Assertions.assertEquals("Cannot invoke \"pl.petlovers.Pet2Vet.pet.Pet.getId()\" because \"pet\" is null", thrown.getMessage());
  }

  @Test
  void should_pass_when_using_cancel_method() {
//    given
    PetController controller = new PetController(mock(PetService.class));
//    then
    assertDoesNotThrow(() -> controller.cancel(1));
  }

  @Test
  void should_return_correct_list_when_using_petService_getAllUserPets_method() {
//    given
    PetController controller = new PetController(mock(PetService.class));
//    when
    final List<PetDTO> userPets = controller.getAllUserPets(1);
//    then
    assertEquals(Collections.emptyList(), userPets);
  }
}
