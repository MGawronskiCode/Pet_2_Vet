package pl.petlovers.Pet2Vet.appUser;


import lombok.*;
import pl.petlovers.Pet2Vet.note.Note;
import pl.petlovers.Pet2Vet.pet.Pet;

import javax.persistence.*;
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

    private String name;
    private String sex;
    private String login;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Pet> pets;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<Note> notes;

    public void modify(AppUser user) {
        setName(user.getName());
        setSex(user.getSex());
        setLogin(user.getLogin());
        setPassword(user.getPassword());

        // TODO: discus this
//        setPets(user.getPets());
//        setNotes(user.getNotes());
    }
}
