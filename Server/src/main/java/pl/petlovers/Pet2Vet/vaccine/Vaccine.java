package pl.petlovers.Pet2Vet.vaccine;

import lombok.*;
import pl.petlovers.Pet2Vet.pet.Pet;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

  @OneToMany//todo
  private List<Pet> pets;

  private String dateTime;

  @PrePersist
  protected void onCreate() {
    dateTime = LocalDateTime.now().toString();
  } //todo set the time provided by user


  public void modify(Vaccine newData){
    this.setName(newData.getName());
//    this.setPet(newData.getPet());
    this.setDateTime(newData.getDateTime());
  }
}
