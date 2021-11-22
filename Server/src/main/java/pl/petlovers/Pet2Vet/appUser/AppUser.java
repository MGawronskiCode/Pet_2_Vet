package pl.petlovers.Pet2Vet.appUser;


import lombok.*;
import org.apache.commons.lang3.StringUtils;
import pl.petlovers.Pet2Vet.note.Note;
import pl.petlovers.Pet2Vet.pet.Pet;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    public void addNote(Note note) {
        note.setCreated(LocalDateTime.now());
        note.setModified(null);
        note.setPet(null);
        note.setAppUser(this);
        this.notes.add(note);
    }

    public void modify(AppUser user) {
        if (StringUtils.isNoneBlank(user.getName())) {
            this.setName(user.getName());
        }
        if (StringUtils.isNoneBlank(user.getSex())) {
            this.setSex(user.getSex());
        }
        if (StringUtils.isNoneBlank(user.getLogin())) {
            this.setLogin(user.getLogin());
        }
        if (StringUtils.isNoneBlank(user.getPassword())) {
            this.setPassword(user.getPassword());
        }
    }
}
