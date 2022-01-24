package pl.petlovers.Pet2Vet.pet.controller;

import org.junit.jupiter.api.Test;
import pl.petlovers.Pet2Vet.Sex;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.specie.PetSpecie;
import pl.petlovers.Pet2Vet.specie.controller.PetSpecieDTO;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PetDTOTest {

  @Test
  void should_create_correct_object_when_using_no_args_constructor() {
//    given
    PetDTO petDTO;
//    when
    petDTO = new PetDTO();
//    then
    assertNotNull(petDTO);
    assertNull(petDTO.getId());
    assertNull(petDTO.getName());
    assertNull(petDTO.getSex());
    assertNull(petDTO.getBirthday());
  }

  @Test
  void should_create_correct_object_when_using_all_args_constructor() {
    //    given
    PetDTO petDTO;
//    when
    petDTO = new PetDTO(1L, "test", Sex.MALE, LocalDate.of(2000, 1, 1), new PetSpecieDTO());
//    then
    assertNotNull(petDTO);

    assertEquals(1L, petDTO.getId());
    assertEquals("test", petDTO.getName());
    assertEquals(Sex.MALE, petDTO.getSex());
    assertEquals(LocalDate.of(2000, 1, 1), petDTO.getBirthday());
  }

  @Test
  void should_create_correct_object_when_using_of_method() {
//    given
    Pet pet = new Pet();
    pet.setId(1L);
    pet.setName("test");
    pet.setSex(Sex.MALE);
    pet.setBirthday(LocalDate.of(2000, 1, 1));
    pet.setSpecie(new PetSpecie());
//    when
    PetDTO petDTO = PetDTO.of(pet);
//    then
    assertEquals(1L, petDTO.getId());
    assertEquals("test", petDTO.getName());
    assertEquals(Sex.MALE, petDTO.getSex());
    assertEquals(LocalDate.of(2000, 1, 1), petDTO.getBirthday());
    assertEquals(new PetSpecieDTO(), petDTO.getSpecie());

  }

  @Test
  void should_create_correct_object_when_using_toPet_method() {
//    given
    PetDTO petDTO = new PetDTO(1L, "test", Sex.MALE, LocalDate.of(2000, 1, 1), new PetSpecieDTO());
//    when
    Pet pet = petDTO.toPet();
//    then
    assertEquals(1L, pet.getId());
    assertEquals("test", pet.getName());
    assertEquals(Sex.MALE, pet.getSex());
    assertEquals(LocalDate.of(2000, 1, 1), pet.getBirthday());

  }



}
