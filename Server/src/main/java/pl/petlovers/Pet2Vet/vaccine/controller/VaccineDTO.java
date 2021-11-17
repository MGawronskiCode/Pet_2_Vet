package pl.petlovers.Pet2Vet.vaccine.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.petlovers.Pet2Vet.vaccine.Vaccine;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class VaccineDTO {

  private String name;
  //  private Pet pet;
  private String dateTime;

  public static VaccineDTO of(Vaccine vaccine) {
    return VaccineDTO.builder()
        .name(vaccine.getName())
        .dateTime(vaccine.getDateTime())
//        .pet(vaccine.getPet())
        .build();
  }

  public Vaccine toVaccine() {
    return Vaccine.builder()
        .name(name)
//        .pet(pet)
        .dateTime(dateTime)
        .build();
  }

}
