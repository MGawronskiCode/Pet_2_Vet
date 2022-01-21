package pl.petlovers.Pet2Vet.visit;

import lombok.*;
import pl.petlovers.Pet2Vet.DatabaseObject;
import pl.petlovers.Pet2Vet.file.File;
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
public class Visit extends DatabaseObject {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL)
  private Pet pet;

  @OneToMany
  private List<File> files = new ArrayList<>();

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
    return files.stream().anyMatch(file -> file.getId()==fileId);
  }

  public void modify(Visit visit) {
    this.setPurpose(visit.getPurpose());
    this.setDateTime(visit.getDateTime());
    this.setPlace(visit.getPlace());
    this.setDescription(visit.getDescription());
    this.setRecommendation(visit.getRecommendation());
    }

  }

