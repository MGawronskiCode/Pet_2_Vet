package pl.petlovers.Pet2Vet.pet.controller;

import org.junit.jupiter.api.Test;
import pl.petlovers.Pet2Vet.entities.appUser.AppUser;
import pl.petlovers.Pet2Vet.entities.pet.Pet;
import pl.petlovers.Pet2Vet.entities.pet.PetService;
import pl.petlovers.Pet2Vet.entities.pet.controller.PetController;
import pl.petlovers.Pet2Vet.entities.pet.controller.PetDTO;
import pl.petlovers.Pet2Vet.utills.security.users.AppUserDetails;
import pl.petlovers.Pet2Vet.entities.specie.PetSpecie;
import pl.petlovers.Pet2Vet.entities.specie.controller.PetSpecieDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
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
  void should_return_correct_list_when_using_PetController_get_method_as_admin() {
//    given
    final AppUserDetails loggedUser = mock(AppUserDetails.class);
    when(loggedUser.isAdmin()).thenReturn(true);

    final PetService petServiceMock = mock(PetService.class);
    when(petServiceMock.getAll()).thenReturn(getSamplePetList());

    final PetController controller = new PetController(petServiceMock);
//    when
    final List<PetDTO> petDTOs = controller.get(loggedUser);
//    then
    assertNotNull(petDTOs);
    assertFalse(petDTOs.isEmpty());
//    assertEquals(new PetDTO(1L, "test", null, null, new PetSpecieDTO(null, null)), petDTOs.get(0));
  }

  private List<Pet> getSamplePetList() {

    List<Pet> pets = new ArrayList<>();

    Pet pet1 = new Pet();
    pet1.setId(1L);
    pet1.setName("test");
    pet1.setSpecie(new PetSpecie());
    pets.add(pet1);

    Pet pet2 = new Pet();
    pet2.setId(2L);
    pet2.setName("test2");
    pet2.setSpecie(new PetSpecie());
    pets.add(pet2);

    return pets;
  }

  @Test
  void should_throw_NullPointerException_when_using_PetController_get_method() {
//    given
    final AppUserDetails loggedUser = mock(AppUserDetails.class);
    when(loggedUser.isAdmin()).thenReturn(false);

    final PetService petServiceMock = mock(PetService.class);
    when(petServiceMock.getAll()).thenReturn(getSamplePetList());

    final PetController controller = new PetController(petServiceMock);
//    then
    assertThrows(NullPointerException.class, () -> controller.get(loggedUser));
  }

  @Test
  void should_return_correct_list_when_using_PetController_getAll_with_petId_argument_method() {
//    given
    final AppUserDetails loggedUser = mock(AppUserDetails.class);
    when(loggedUser.isAdmin()).thenReturn(true);

    final PetService petServiceMock = mock(PetService.class);
    when(petServiceMock.get(anyLong())).thenReturn(getSamplePet());

    PetController controller = new PetController(petServiceMock);
//    when
    final PetDTO petDTO = controller.get(1L, loggedUser);
//    then
    assertNotNull(petDTO);
//    assertEquals(new PetDTO(1L, "test", null, null, new PetSpecieDTO(null, null)), petDTO);
  }

  private Pet getSamplePet() {
    Pet pet = new Pet();
    pet.setId(1L);
    pet.setName("test");
    pet.setSpecie(new PetSpecie());
    pet.setAppUsers(new ArrayList<>());

    return pet;
  }

  @Test
  void should_return_correct_object_when_using_petController_create_with_petId_and_petDTO_arguments_method() {
//    given
    final AppUserDetails loggedUser = mock(AppUserDetails.class);
    when(loggedUser.isAdmin()).thenReturn(true);
    final AppUser appUserMock = mock(AppUser.class);
    when(loggedUser.getAppUser()).thenReturn(appUserMock);
    when(appUserMock.getId()).thenReturn(1L);
    final PetDTO petDTO = PetDTO.of(getSamplePet());

    final PetService petServiceMock = mock(PetService.class);
    when(petServiceMock.create(1L, petDTO)).thenReturn(petDTO);

    PetController controller = new PetController(petServiceMock);
//    when
    final PetDTO createdPetDTO = controller.create(petDTO, loggedUser);
//    then
    assertNotNull(createdPetDTO);
//    assertEquals(new PetDTO(1L, "test", null, null, new PetSpecieDTO(null, null)), createdPetDTO);
  }

  @Test
  void should_create_correct_object_when_using_update_method() {
//    given
    final PetService petServiceMock = mock(PetService.class);
    when(petServiceMock.get(1L)).thenReturn(getSamplePet());
    when(petServiceMock.update(1L, PetDTO.of(getSamplePet()))).thenReturn(getSamplePet());
    final AppUserDetails appUserDetailsMock = mock(AppUserDetails.class);
    when(appUserDetailsMock.isAdmin()).thenReturn(true);
    PetController controller = new PetController(petServiceMock);
//    when
    final PetDTO updatedPet = controller.update(1L, PetDTO.of(getSamplePet()), appUserDetailsMock);
//    then
    assertNotNull(updatedPet);
//    assertEquals(new PetDTO(1L, "test", null, null, new PetSpecieDTO(null, null)), updatedPet);
  }

  @Test
  void should_not_pass_when_using_cancel_method() {
//    given
    final PetService petServiceMock = mock(PetService.class);
    final AppUserDetails appUserDetailsMock = mock(AppUserDetails.class);
    when(appUserDetailsMock.isAdmin()).thenReturn(true);
    PetController controller = new PetController(petServiceMock);
//    then
    assertThrows(NullPointerException.class, () -> controller.cancel(1L, appUserDetailsMock));
  }

}
