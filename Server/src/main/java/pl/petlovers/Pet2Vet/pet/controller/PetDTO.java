package pl.petlovers.Pet2Vet.pet.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.petlovers.Pet2Vet.meal.Meal;
import pl.petlovers.Pet2Vet.note.Note;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.specie.PetSpecie;
import pl.petlovers.Pet2Vet.vaccine.Vaccine;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetDTO {

  List<Note> notes;
  private String name;
  private String sex;
  private LocalDateTime birthday;
  private PetSpecie specie;
  private List <Meal> meals;
  private List <Vaccine> vaccines;

  public static PetDTO of(Pet pet) {
    return PetDTO.builder()
        .name(pet.getName())
        .sex(pet.getSex())
        .birthday(pet.getBirthday())
        .specie(pet.getSpecie())
        .vaccines(pet.getVaccines())
        .meals(pet.getMeals())
        .notes(pet.getNotes())
        .build();
  }

  public Pet toPet() {
    return Pet.builder()
        .name(name)
        .sex(sex)
        .birthday(birthday)
        .specie(specie)
        .vaccines(vaccines)
        .meals(meals)
        .notes(notes)
        .build();
  }

}
