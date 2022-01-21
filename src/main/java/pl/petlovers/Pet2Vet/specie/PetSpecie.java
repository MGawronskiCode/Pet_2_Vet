package pl.petlovers.Pet2Vet.specie;

import lombok.*;
import pl.petlovers.Pet2Vet.DatabaseObject;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.specie.controller.PetSpecieDTO;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "specie")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PetSpecie extends DatabaseObject {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "specie_id")
  private List<Pet> pets;

  @Column(nullable = false)
  private boolean isDeleted;

  public void modify(PetSpecieDTO newData){
    if (newData.getName() != null) {
      this.setName(newData.getName());
    }
  }

  @Override
  public String toString() {
    return "PetSpecie{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
  }

}
