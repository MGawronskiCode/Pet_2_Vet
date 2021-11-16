package pl.petlovers.Pet2Vet.vaccines;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VaccineDTO {

  private long id;
  private String name;
  //  private Pet pet;
  private LocalDateTime dateTime;

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
