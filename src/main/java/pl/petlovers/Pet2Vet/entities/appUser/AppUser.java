package pl.petlovers.Pet2Vet.entities.appUser;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.petlovers.Pet2Vet.entities.appUser.controller.AppUserDTO;
import pl.petlovers.Pet2Vet.entities.note.Note;
import pl.petlovers.Pet2Vet.entities.pet.Pet;
import pl.petlovers.Pet2Vet.utills.security.users.Roles;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
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

  @Column(nullable = false)
  @Enumerated
  private Roles role;

  @JsonIgnore
  @ManyToMany()
  @JoinTable(
      name = "Users_pets",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "pet_id"
      )
  )
  private List<Pet> pets;

  @JsonIgnore
  @OneToMany(
      mappedBy = "appUser",
      fetch = FetchType.LAZY
  )
  private List<Note> notes;

  @Column(nullable = false)
  private boolean isDeleted;

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

//  todo modify login
  public void modify(AppUserDTO user) {
    modifyName(user);
    modifySex(user);
    modifyRole(user);
  }

  private void modifyName(AppUserDTO user) {
    if (StringUtils.isNoneBlank(user.getName())) {
      this.setName(user.getName());
    }
  }

  private void modifySex(AppUserDTO user) {
    final Sex userSex = user.getSex();
    if (userSex != null && StringUtils.isNoneBlank(userSex.toString())) {
      this.setSex(userSex);
    }
  }

  private void modifyRole(AppUserDTO user) {
    if (user.getRole() != null) {
      this.setRole(user.getRole());
    }
  }

  public void modifyPassword(String password) {
    if (StringUtils.isNoneBlank(password)) {
      this.setPassword(new BCryptPasswordEncoder().encode(password));
    }
  }

  public void modifyLogin(String login) {
    if (StringUtils.isNoneBlank(login)) {
      this.setLogin(login);
    }
  }

  public void addPetToPetsList(Pet pet) {
    this.pets.add(pet);
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
  public String toString() {
    return "AppUser{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", sex=" + sex +
        ", login='" + login + '\'' +
        ", password='" + password + '\'' +
        ", role=" + role +
        ", isDeleted=" + isDeleted +
        '}';
  }
}
