package pl.petlovers.Pet2Vet.vaccine;

import lombok.*;
import org.hibernate.Hibernate;
import pl.petlovers.Pet2Vet.Deletable;
import pl.petlovers.Pet2Vet.vaccine.controller.VaccineDTO;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Vaccine implements Deletable {

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

  @Override
  public void delete() {
    this.isDeleted = true;
  }

  @Override
  public void restore() {
    this.isDeleted = false;
  }

  @Override
  public boolean isDeleted() {
    return this.isDeleted;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Vaccine vaccine = (Vaccine) o;
    return id != null && Objects.equals(id, vaccine.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
