package pl.petlovers.Pet2Vet.pet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.petlovers.Pet2Vet.appUser.AppUserService;
import pl.petlovers.Pet2Vet.exceptions.not_found_exceptions.PetNotFoundException;
import pl.petlovers.Pet2Vet.pet.controller.PetDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PetServiceTest {

  @Test
  void should_correctly_initialize_petService_when_using_constructor() {
//    given
    PetService service1 = mock(PetService.class);
    PetService service2 = new PetService(mock(PetRepository.class), mock(AppUserService.class));
//    then
    assertNotNull(service1);
    assertNotNull(service2);
  }

  @Test
  void should_return_correct_list_when_using_petService_getAll_method() {
//    given
    PetService service = new PetService(mock(PetRepository.class), mock(AppUserService.class));
    when(service.getAll()).thenReturn(getSamplePetList());
//    when
    final List<Pet> pets = service.getAll();
//    then
    assertEquals(2, pets.size());
    assertEquals(1, pets.get(0).getId());
  }

  private List<Pet> getSamplePetList() {
    List<Pet> pets = new ArrayList<>();

    Pet pet1 = new Pet();
    pet1.setId(1L);
    pet1.setName("test");
    pets.add(pet1);

    Pet pet2 = new Pet();
    pet2.setId(2L);
    pet2.setName("test2");
    pets.add(pet2);

    return pets;
  }

  @Test
  void should_return_correct_object_when_using_create_no_arguments_method() {
//    given
    PetService service = new PetService(mock(PetRepository.class), mock(AppUserService.class));
    PetDTO petDTO = new PetDTO();
    petDTO.setId(1L);
//    when
    final PetDTO createdPetDTO = service.create(petDTO);
//    then
    assertNotNull(createdPetDTO);
    assertEquals("PetDTO(id=1, name=null, sex=null, birthday=null)", createdPetDTO.toString());
  }

  @Test
  void should_return_correct_object_when_using_create_with_id_argument_method() {
//    given
    //todo jak nadpisać metodę w mocku albo jak stworzyć własnego mocka
    PetService service = new PetService(mock(PetRepository.class), mock(AppUserService.class));
    PetDTO petDTO = new PetDTO();
    petDTO.setId(1L);
//    when
    final PetDTO createdPetDTO = service.create(5, petDTO);
//    then
    assertNotNull(createdPetDTO);
    assertEquals("PetDTO(id=1, name=null, sex=null, birthday=null)", createdPetDTO.toString());
  }

  @Test
  void should_return_correct_object_when_using_update_with_id_argument_method() {
//    given
    PetService service = new PetService(mock(PetRepository.class), mock(AppUserService.class));

    PetDTO petDTO = new PetDTO();
    petDTO.setId(1L);
    petDTO.setName("test1");

    PetDTO newPetData = new PetDTO();
    newPetData.setName("test2");
//    when
    final Pet updatedPet = service.update(1L, newPetData);
//    then
    assertNotNull(updatedPet);
    assertEquals("Pet{id=1, name='test2', sex='null', birthday=null}", updatedPet.toString());
  }

  @Test
  void should_not_stop_when_calling_petService_delete_method() {
//    given
    PetService service = new PetService(mock(PetRepository.class), mock(AppUserService.class));
//    then
    PetNotFoundException thrown = Assertions.assertThrows(PetNotFoundException.class, () -> service.delete(1));

    Assertions.assertEquals("Could not find pet with id: 1", thrown.getMessage());
  }

  @Test
  void should_return_correct_object_when_using_getUsersPets_with_id_argument_method() {
//    given
    PetService service = new PetService(mock(PetRepository.class), mock(AppUserService.class));
//    when
    NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> service.getUserPets(1));
//    then
    assertNotNull(service);
    Assertions.assertEquals("Cannot invoke \"pl.petlovers.Pet2Vet.appUser.AppUser.getPets()\" because \"user\" is null", thrown.getMessage());
  }

}
