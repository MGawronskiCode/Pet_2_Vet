package pl.petlovers.Pet2Vet.appUser;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.petlovers.Pet2Vet.Sex;
import pl.petlovers.Pet2Vet.appUser.controller.AppUserDTO;
import pl.petlovers.Pet2Vet.note.Note;
import pl.petlovers.Pet2Vet.pet.Pet;
import pl.petlovers.Pet2Vet.security.Roles;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppUser implements UserDetails {

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

    @Column(nullable = false)
    @Enumerated
    private Roles role;

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

    public AppUser(String name, Sex sex, String login, String password, Roles role) {
        this.name = name;
        this.sex = sex;
        this.login = login;
        this.password = password;
        this.role = role;
    }

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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(String.valueOf(role)));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
