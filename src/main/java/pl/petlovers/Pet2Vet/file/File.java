package pl.petlovers.Pet2Vet.file;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class File {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String url;
  private LocalDateTime created;
  private LocalDateTime modified;

  @Column(nullable = false)
  private boolean isDeleted;

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

  public boolean isDeleted() {
    return isDeleted;
  }

  public void delete() {
    isDeleted = true;
  }

  public void restore() {
    isDeleted = false;
  }
}
