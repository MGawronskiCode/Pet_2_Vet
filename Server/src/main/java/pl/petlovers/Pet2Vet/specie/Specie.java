package pl.petlovers.Pet2Vet.specie;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public enum Specie /*(Class of pet)*/ {

  MAMMAL, BIRD, REPTILE, AMPHIBIAN;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

}
