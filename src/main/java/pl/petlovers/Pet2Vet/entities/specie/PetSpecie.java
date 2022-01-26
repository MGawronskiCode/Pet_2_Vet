package pl.petlovers.Pet2Vet.entities.specie;

import lombok.*;
import org.hibernate.Hibernate;
import pl.petlovers.Pet2Vet.entities.pet.Pet;
import pl.petlovers.Pet2Vet.entities.specie.controller.PetSpecieDTO;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "specie")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PetSpecie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "specie_id")
  @ToString.Exclude
  private List<Pet> pets;

  @Column(nullable = false)
  private boolean isDeleted;

  public void modify(PetSpecieDTO newData) {
    if (newData.getName() != null) {
      this.setName(newData.getName());
    }
  }

  public boolean isDeleted() {
    return this.isDeleted;
  }

  public void delete() {
    this.isDeleted = true;
  }

  public void restore() {
    this.isDeleted = false;
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    PetSpecie petSpecie = (PetSpecie) o;
    return id != null && Objects.equals(id, petSpecie.id);
  }

  @Override
  public String toString() {
    return "PetSpecie{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
