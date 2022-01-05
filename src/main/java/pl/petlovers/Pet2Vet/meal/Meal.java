package pl.petlovers.Pet2Vet.meal;

import lombok.*;
import pl.petlovers.Pet2Vet.pet.Pet;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
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


    public void modify(Meal meal) {
        setFood(meal.getFood());
        setFoodAmount(meal.getFoodAmount());
        setExpectedFeedingTime(meal.getExpectedFeedingTime());
        setActualFeedingTime(meal.getActualFeedingTime());
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