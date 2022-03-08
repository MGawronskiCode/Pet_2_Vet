package pl.petlovers.Pet2Vet.entities.pet;

import lombok.*;
import org.hibernate.Hibernate;
import pl.petlovers.Pet2Vet.entities.appUser.Sex;
import pl.petlovers.Pet2Vet.entities.appUser.AppUser;
import pl.petlovers.Pet2Vet.entities.meal.Meal;
import pl.petlovers.Pet2Vet.entities.note.Note;
import pl.petlovers.Pet2Vet.entities.visit.Visit;
import pl.petlovers.Pet2Vet.entities.specie.PetSpecie;
import pl.petlovers.Pet2Vet.entities.vaccine.Vaccine;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Pet {

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "pet_id")
  @ToString.Exclude
  List<Meal> meals;
  @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @ToString.Exclude
  List<Note> notes;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @Enumerated
  private Sex sex;
  private LocalDate birthday;
  @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
  private PetSpecie specie;
  @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
  @ToString.Exclude
  private List<Vaccine> vaccines;
  @ManyToMany(mappedBy = "pets", fetch = FetchType.LAZY)
  @ToString.Exclude
  private List<AppUser> appUsers;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @ToString.Exclude
  private List<Visit> visits;

  @Column(nullable = false)
  private boolean isDeleted;

  public void addNote(Note note) {
    note.setCreated(LocalDateTime.now());
    note.setModified(null);
    note.setAppUser(null);
    note.setPet(this);
    this.notes.add(note);
  }

  public void addMeal(Meal meal) {
    this.meals.add(meal);
  }

  public void addVisit(Visit visit) {
    this.visits.add(visit);
  }

  public void modify(Pet newData) {
    if (newData.getName() != null) {
      this.setName(newData.getName());
    }
    if (newData.getSex() != null) {
      this.setSex(newData.getSex());
    }
    if (newData.getBirthday() != null) {
      this.setBirthday(newData.getBirthday());
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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Pet pet = (Pet) o;
    return id != null && Objects.equals(id, pet.id);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "Pet{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", sex='" + sex + '\'' +
        ", birthday=" + birthday +
        '}';
  }
}
