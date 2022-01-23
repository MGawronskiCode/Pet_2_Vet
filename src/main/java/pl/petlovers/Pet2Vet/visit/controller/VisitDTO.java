package pl.petlovers.Pet2Vet.visit.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.petlovers.Pet2Vet.file.File;
import pl.petlovers.Pet2Vet.visit.Visit;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitDTO {

  private Long id;
  private List<File> files;
  private String purpose;
  private LocalDateTime dateTime;
  private String place;
  private String description;
  private String recommendation;

  public static VisitDTO of(Visit visit) {
    return VisitDTO.builder()
            .id(visit.getId())
      .files(visit.getFiles())
      .purpose(visit.getPurpose())
      .dateTime(visit.getDateTime())
      .place(visit.getPlace())
      .description(visit.getDescription())
      .recommendation(visit.getRecommendation())
      .build();
  }

  public Visit toVisit() {
    return Visit.builder()
            .id(id)
      .files(files)
      .purpose(purpose)
      .dateTime(dateTime)
      .place(place)
      .description(description)
      .recommendation(recommendation)
      .build();
  }
}
