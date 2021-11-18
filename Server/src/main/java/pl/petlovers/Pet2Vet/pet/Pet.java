package pl.petlovers.Pet2Vet.pet;

import lombok.*;
import pl.petlovers.Pet2Vet.note.Note;
import pl.petlovers.Pet2Vet.specie.PetSpecie;
import pl.petlovers.Pet2Vet.vaccine.Vaccine;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Pet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String sex;

  private LocalDateTime birthday;

  @OneToOne
  @JoinColumn(name = "specie")
  private PetSpecie specie;

  @OneToMany
  @JoinColumn(name = "vaccines_id")
  List<Vaccine> vaccines;

//  @OneToMany
//@JoinColumn(name = "meals_id")
//  List <Meal> meals;

  @OneToMany
  @JoinColumn(name = "notes_id")
  List <Note> notes;
}
