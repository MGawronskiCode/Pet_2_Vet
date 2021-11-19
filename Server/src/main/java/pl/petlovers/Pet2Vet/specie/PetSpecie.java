package pl.petlovers.Pet2Vet.specie;

import lombok.*;

import javax.persistence.*;

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

}
