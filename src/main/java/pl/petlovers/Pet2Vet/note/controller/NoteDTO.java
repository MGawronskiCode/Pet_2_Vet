package pl.petlovers.Pet2Vet.note.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.petlovers.Pet2Vet.note.Note;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class NoteDTO {

  private Long id;
  private String title;
  private String content;

  public static NoteDTO of(Note note) {
    return NoteDTO.builder()
        .id(note.getId())
        .title(note.getTitle())
        .content(note.getContent())
        .build();
  }

  public Note toNote() {
    return Note.builder()
        .id(id)
        .title(title)
        .content(content)
        .build();
  }
}
