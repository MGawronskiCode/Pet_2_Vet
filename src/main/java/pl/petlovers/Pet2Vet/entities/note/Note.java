package pl.petlovers.Pet2Vet.entities.note;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import pl.petlovers.Pet2Vet.entities.appUser.AppUser;
import pl.petlovers.Pet2Vet.entities.file.File;
import pl.petlovers.Pet2Vet.entities.pet.Pet;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Note {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private AppUser appUser;

  @ManyToOne
  @JoinColumn(name = "pet_id")
  private Pet pet;

  @JsonIgnore
  @OneToMany(
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY
  )
  @ToString.Exclude
  private List<File> files;

  private String title;
  private String content;
  private LocalDateTime created;
  private LocalDateTime modified; //OffsetDateTime / ZonedDateTime

  @Column(nullable = false)
  private boolean isDeleted;

  public void addFile(File file) {
    file.setCreated(LocalDateTime.now());
    this.files.add(file);
  }

  public boolean containsFile(long fileId) {
    return files.stream().anyMatch(file -> file.getId() == fileId);
  }

  protected void modify(Note note) {
    if (StringUtils.isNoneBlank(note.getTitle())) {
      this.setTitle(note.getTitle());
      setModified(LocalDateTime.now());
    }
    if (StringUtils.isNoneBlank(note.getContent())) {
      this.setContent(note.getContent());
      setModified(LocalDateTime.now());
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
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Note note = (Note) o;
    return id != null && Objects.equals(id, note.id);
  }

  @Override
  public String toString() {
    return "Note{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        ", created=" + created +
        ", modified=" + modified +
        '}';
  }
}
