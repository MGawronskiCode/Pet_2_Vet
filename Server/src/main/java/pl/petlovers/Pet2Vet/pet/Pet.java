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

  @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
  private PetSpecie specie;

  @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
  private List<Vaccine> vaccines;

  //  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//  @JoinColumn(name = "pet_id")
//  List <Meal> meals;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "pet_id")
  List<Note> notes;

}
