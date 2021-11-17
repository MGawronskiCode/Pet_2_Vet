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

//  private Pet pet;

  private String dateTime;

  @PrePersist
  protected void onCreate() {
    dateTime = LocalDateTime.now().toString();
  } //todo set the time provided by user

}
