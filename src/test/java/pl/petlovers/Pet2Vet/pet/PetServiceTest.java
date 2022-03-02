package pl.petlovers.Pet2Vet.pet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.petlovers.Pet2Vet.entities.appUser.AppUser;
import pl.petlovers.Pet2Vet.entities.appUser.AppUserService;
import pl.petlovers.Pet2Vet.entities.appUser.controller.AppUserDTO;
import pl.petlovers.Pet2Vet.entities.pet.Pet;
import pl.petlovers.Pet2Vet.entities.pet.PetRepository;
import pl.petlovers.Pet2Vet.entities.pet.PetService;
import pl.petlovers.Pet2Vet.entities.pet.controller.PetDTO;
import pl.petlovers.Pet2Vet.utills.exceptions.not_found_exceptions.PetNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
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
//    when
    final PetDTO createdPetDTO = service.create(getNewPetData());
//    then
    assertNotNull(createdPetDTO);
    assertEquals("PetDTO(id=1, name=test, sex=null, birthday=null)", createdPetDTO.toString());
  }

  private PetDTO getNewPetData() {
    PetDTO petDTO = new PetDTO();
    petDTO.setId(1L);
    petDTO.setName("test");
    petDTO.setSex(null);
    petDTO.setBirthday(null);
//    petDTO.setSpecie(new PetSpecieDTO());

    return petDTO;
  }

  @Test
  void should_return_correct_object_when_using_create_with_id_argument_method() {
//    given
    final PetRepository petRepositoryMock = mock(PetRepository.class);
    final AppUserService appUserServiceMock = mock(AppUserService.class);
    PetService service = new PetService(petRepositoryMock, appUserServiceMock);
    when(petRepositoryMock.save(getNewPetData().toPet())).thenReturn(getNewPetData().toPet());
    final AppUser appUserMock = mock(AppUser.class);
    when(appUserServiceMock.get(anyLong())).thenReturn(appUserMock);
    when(appUserServiceMock.update(1L, AppUserDTO.of(appUserMock))).thenReturn(mock(AppUser.class));
//    when
    final PetDTO createdPetDTO = service.create(1L, getNewPetData());
//    then
    assertNotNull(createdPetDTO);
    assertEquals("PetDTO(id=1, name=test, sex=null, birthday=null)", createdPetDTO.toString());
  }

  @Test
  void should_return_correct_object_when_using_update_with_id_argument_method() {
//    given
    final AppUserService appUserServiceMock = mock(AppUserService.class);
    final PetRepository petRepositoryMock = mock(PetRepository.class);
    PetService service = new PetService(petRepositoryMock, appUserServiceMock);

    when(petRepositoryMock.getById(anyLong())).thenReturn(getNewPetData().toPet());
    when(petRepositoryMock.save(getNewPetData().toPet())).thenReturn(getNewPetData().toPet());
//    when
    final Pet updatedPet = service.update(1L, getNewPetData());
//    then
    assertNotNull(updatedPet);
    assertEquals("Pet{id=1, name='test', sex='null', birthday=null}", updatedPet.toString());
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
  void should_return_correct_object_when_using_get_method_when_pet_is_not_deleted() {
//    given
    final PetRepository petRepositoryMock = mock(PetRepository.class);
    when(petRepositoryMock.findById(1L)).thenReturn(Optional.of((getNewPetData().toPet())));
    PetService service = new PetService(petRepositoryMock, mock(AppUserService.class));
//    when
    final Pet pet = service.get(1L);
//    then
    assertNotNull(pet);
    assertEquals("Pet{id=1, name='test', sex='null', birthday=null}", pet.toString());
  }


  @Test
  void should_throw_PetNotFoundException_when_using_get_method_when_pet_is_deleted() {
//    given
    final PetRepository petRepositoryMock = mock(PetRepository.class);
    Pet pet = new Pet();
    pet.delete();
    when(petRepositoryMock.findById(1L)).thenReturn(Optional.of(pet));
    PetService service = new PetService(petRepositoryMock, mock(AppUserService.class));
//    then
    assertThrows(PetNotFoundException.class, () -> service.get(1L));
  }
}
