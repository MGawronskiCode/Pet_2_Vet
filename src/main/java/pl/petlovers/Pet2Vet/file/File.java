package pl.petlovers.Pet2Vet.file;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import pl.petlovers.Pet2Vet.note.Note;
import pl.petlovers.Pet2Vet.visit.Visit;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class File {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

//  @ManyToOne(cascade = CascadeType.ALL)
////  TODO join column??
//  private Note note;
//
////  TODO Visit visit? Many to one
//
//  @ManyToOne(cascade = CascadeType.ALL)
//  private Visit visit;

  private String name;
  private String url;

  private LocalDateTime created;
  private LocalDateTime modified;

  protected void modify(File file) {
    if (StringUtils.isNoneBlank(file.getName())) {
      this.setName(file.getName());
      setModified(LocalDateTime.now());
    }
    if (StringUtils.isNoneBlank(file.getUrl())) {
      this.setUrl(file.getUrl());
      setModified(LocalDateTime.now());
    }
  }


}
