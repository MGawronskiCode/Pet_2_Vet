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
public class VaccineDTO {

  private Long id;
  private String name;
  private String dateTime;

  public static VaccineDTO of(Vaccine vaccine) {
    return VaccineDTO.builder()
            .id(vaccine.getId())
        .name(vaccine.getName())
        .dateTime(vaccine.getDateTime())
        .build();
  }

  public Vaccine toVaccine() {
    return Vaccine.builder()
            .id(id)
        .name(name)
        .dateTime(dateTime)
        .build();
  }

}
