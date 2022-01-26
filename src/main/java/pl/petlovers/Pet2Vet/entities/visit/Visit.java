package pl.petlovers.Pet2Vet.entities.visit;

import lombok.*;
import org.hibernate.Hibernate;
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
public class Visit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL)
  private Pet pet;

  @OneToMany
  @ToString.Exclude
  private List<File> files;

  private String purpose;
  private LocalDateTime dateTime;
  private String place;
  private String description;
  private String recommendation;

  @Column(nullable = false)
  private boolean isDeleted;

  public void addFile(File file) {
    file.setCreated(LocalDateTime.now());
    this.files.add(file);
  }

  public boolean containsFile(long fileId) {
    return files.stream().anyMatch(file -> file.getId() == fileId);
  }

  public void modify(Visit visit) {
    this.setPurpose(visit.getPurpose());
    this.setDateTime(visit.getDateTime());
    this.setPlace(visit.getPlace());
    this.setDescription(visit.getDescription());
    this.setRecommendation(visit.getRecommendation());
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
    Visit visit = (Visit) o;
    return id != null && Objects.equals(id, visit.id);
  }
}

