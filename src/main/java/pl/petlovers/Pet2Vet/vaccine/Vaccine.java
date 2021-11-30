package pl.petlovers.Pet2Vet.vaccine;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Vaccine {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false, unique = true)
  private Long id;

  private String name;

  private String dateTime;

  public void modify(Vaccine newData){
    this.setName(newData.getName());
    this.setDateTime(newData.getDateTime());
  }
}
