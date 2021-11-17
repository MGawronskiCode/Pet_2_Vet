package pl.petlovers.Pet2Vet.note;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private LocalDateTime created;
    private LocalDateTime modified; //OffsetDateTime / ZonedDateTime

    protected void modify(Note note) {
        setTitle(note.getTitle());
        setContent(note.getContent());
        setModified(LocalDateTime.now());
    }
}
