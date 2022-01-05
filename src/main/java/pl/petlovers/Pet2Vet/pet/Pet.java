package pl.petlovers.Pet2Vet.pet;

import lombok.*;
import pl.petlovers.Pet2Vet.Sex;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.meal.Meal;
import pl.petlovers.Pet2Vet.note.Note;
import pl.petlovers.Pet2Vet.specie.PetSpecie;
import pl.petlovers.Pet2Vet.vaccine.Vaccine;
import pl.petlovers.Pet2Vet.visit.Visit;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Enumerated
    private Sex sex;

    private LocalDate birthday;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private PetSpecie specie;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Vaccine> vaccines;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    List<Meal> meals;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<Note> notes;

    @ManyToMany(mappedBy = "pets", cascade = CascadeType.ALL)
    private List<AppUser> appUsers;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Visit> visits = new ArrayList<>();

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

    public void modify(Pet newData){
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
