package pl.petlovers.Pet2Vet.appUser;


import lombok.*;
import pl.petlovers.Pet2Vet.note.Note;
import pl.petlovers.Pet2Vet.pet.Pet;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "User")
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
    private String sex;
    @Column(nullable = false, unique = true)
    private String login;
    @Column(nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "Users_pets",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id")
    )
    private List<Pet> pets = new ArrayList<>();

    @OneToMany(
            mappedBy = "appUser",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Note> notes = new ArrayList<>();

    //    TODO: check if data exist
    public void modify(AppUser user) {
        setName(user.getName());
        setSex(user.getSex());
        setLogin(user.getLogin());
        setPassword(user.getPassword());
        setPets(user.getPets());
        setNotes(user.getNotes());
    }
}
