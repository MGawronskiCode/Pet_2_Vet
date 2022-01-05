package pl.petlovers.Pet2Vet.appUser;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import pl.petlovers.Pet2Vet.Sex;
import pl.petlovers.Pet2Vet.appUser.controller.AppUserDTO;
import pl.petlovers.Pet2Vet.note.Note;
import pl.petlovers.Pet2Vet.pet.Pet;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Enumerated
    private Sex sex;
    @Column(nullable = false, unique = true)
    private String login;
    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "Users_pets",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id")
    )
    private List<Pet> pets = new ArrayList<>();

    @JsonIgnore
    @OneToMany(
            mappedBy = "appUser",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Note> notes = new ArrayList<>();

    public void addNote(Note note) {
        note.setCreated(LocalDateTime.now());
        note.setModified(null);
        note.setPet(null);
        note.setAppUser(this);
        this.notes.add(note);
    }

    public void modify(AppUserDTO user) {
        if (StringUtils.isNoneBlank(user.getName())) {
            this.setName(user.getName());
        }
        if (StringUtils.isNoneBlank(user.getSex().toString())) {
            this.setSex(user.getSex());
        }
    }

    public void addPetToPetsList(Pet pet){
        this.pets.add(pet);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
