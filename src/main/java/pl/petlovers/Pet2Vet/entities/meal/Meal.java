package pl.petlovers.Pet2Vet.entities.meal;

import lombok.*;
import org.hibernate.Hibernate;
import pl.petlovers.Pet2Vet.entities.pet.Pet;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Meal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String food;
  private double foodAmount;
  private LocalDateTime expectedFeedingTime;
  private LocalDateTime actualFeedingTime;

  @ManyToOne
  @JoinColumn(name = "pet_id")
  private Pet pet;

  @Column(nullable = false)
  private boolean isDeleted;


  public void modify(Meal meal) {
    setFood(meal.getFood());
    setFoodAmount(meal.getFoodAmount());
    setExpectedFeedingTime(meal.getExpectedFeedingTime());
    setActualFeedingTime(meal.getActualFeedingTime());
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
    Meal meal = (Meal) o;
    return id != null && Objects.equals(id, meal.id);
  }

  @Override
  public String toString() {
    return "Meal{" +
        "id=" + id +
        ", food='" + food + '\'' +
        ", foodAmount=" + foodAmount +
        ", expectedFeedingTime=" + expectedFeedingTime +
        ", actualFeedingTime=" + actualFeedingTime +
        '}';
  }
}
