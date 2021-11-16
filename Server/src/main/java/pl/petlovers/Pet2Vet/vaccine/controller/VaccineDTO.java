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

  private Long id;
  private String name;
  //  private Pet pet;
  private String dateTime;

  public static VaccineDTO of(Vaccine vaccine) {
    return new VaccineDTO(
          vaccine.getId()
        , vaccine.getName()
//        ,vaccine.getPet()
        , vaccine.getDateTime());
  }

  public Vaccine toVaccine() {
    return Vaccine.builder()
        .id(id)
        .name(name)
//        .pet(pet)
        .dateTime(dateTime)
        .build();
  }

}
