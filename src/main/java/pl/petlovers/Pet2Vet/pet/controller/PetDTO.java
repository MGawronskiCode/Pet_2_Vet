package pl.petlovers.Pet2Vet.pet.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.petlovers.Pet2Vet.Sex;
import pl.petlovers.Pet2Vet.pet.Pet;

import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetDTO {

  private Long id;
  private String name;

  @Enumerated
  private Sex sex;

  private LocalDate birthday;

  public static PetDTO of(Pet pet) {
    return PetDTO.builder()
        .id(pet.getId())
        .name(pet.getName())
        .sex(pet.getSex())
        .birthday(pet.getBirthday())
        .build();
  }

  public Pet toPet() {
    return Pet.builder()
        .id(id)
        .name(name)
        .sex(sex)
        .birthday(birthday)
        .build();
  }
}
