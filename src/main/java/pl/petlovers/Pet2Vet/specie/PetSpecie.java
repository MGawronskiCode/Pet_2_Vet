package pl.petlovers.Pet2Vet.specie;

import lombok.*;
import pl.petlovers.Pet2Vet.pet.Pet;

import javax.persistence.*;
import java.util.List;

@Data
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
  private List<Pet> pets;

  public void modify(PetSpecie newData){
    this.setName(newData.getName());
    // TODO: line below make doesn't allow to modify the Pet's specie (PUT method in PetSpecieController)
//    this.setPets(newData.getPets());
  }

  @Override
  public String toString() {
    return "PetSpecie{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
  }
}
