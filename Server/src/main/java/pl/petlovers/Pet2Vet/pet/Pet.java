package pl.petlovers.Pet2Vet.pet;

import lombok.*;
import pl.petlovers.Pet2Vet.appUser.AppUser;
import pl.petlovers.Pet2Vet.meal.Meal;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    List<Meal> meals;

    @OneToMany(
            mappedBy = "pet",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    List<Note> notes;

    @ManyToMany(mappedBy = "pets")
    private List<AppUser> appUsers;

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

    public void modify(Pet newData){
        this.setName(newData.getName());
        this.setSex(newData.getSex());
        this.setBirthday(newData.getBirthday());
        this.setSpecie(newData.getSpecie());
        this.setVaccines(newData.getVaccines());
        this.setMeals(newData.getMeals());
        this.setNotes(newData.getNotes());
        this.setAppUsers(newData.getAppUsers());
    }
}
