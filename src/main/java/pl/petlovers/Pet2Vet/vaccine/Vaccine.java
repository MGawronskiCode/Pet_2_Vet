package pl.petlovers.Pet2Vet.vaccine;

import lombok.*;
import pl.petlovers.Pet2Vet.DatabaseObject;
import pl.petlovers.Pet2Vet.vaccine.controller.VaccineDTO;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Vaccine extends DatabaseObject {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false, unique = true)
  private Long id;

  private String name;

  private String dateTime;

  @Column(nullable = false)
  private boolean isDeleted;

  public void modify(VaccineDTO newData){
    if (newData.getName() != null) {
      this.setName(newData.getName());
    }
    if (newData.getDateTime() != null) {
      this.setDateTime(newData.getDateTime());
    }
  }

  @Override
  public String toString() {
    return "Vaccine{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", dateTime='" + dateTime + '\'' +
            '}';
  }

}
