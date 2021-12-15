package pl.petlovers.Pet2Vet.pet.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.petlovers.Pet2Vet.Sex;
import pl.petlovers.Pet2Vet.meal.Meal;
import pl.petlovers.Pet2Vet.note.Note;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.specie.PetSpecie;
import pl.petlovers.Pet2Vet.vaccine.Vaccine;

import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetDTO {

  private String name;

  @Enumerated
  private Sex sex;

  private LocalDate birthday;

  public static PetDTO of(Pet pet) {
    return PetDTO.builder()
        .name(pet.getName())
        .sex(pet.getSex())
        .birthday(pet.getBirthday())
        .build();
  }

  public Pet toPet() {
    return Pet.builder()
        .name(name)
        .sex(sex)
        .birthday(birthday)
        .build();
  }
}
