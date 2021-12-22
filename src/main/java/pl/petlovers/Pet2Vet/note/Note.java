package pl.petlovers.Pet2Vet.note;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import pl.petlovers.Pet2Vet.appUser.AppUser;
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
    private List<File> files = new ArrayList<>();

    private String title;
    private String content;
    private LocalDateTime created;
    private LocalDateTime modified; //OffsetDateTime / ZonedDateTime

    public void addFile(File file) {
        file.setCreated(LocalDateTime.now());
        this.files.add(file);
    }

    public boolean containsFile(long fileId) {
        return files.stream().anyMatch(file -> file.getId()==fileId);
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
