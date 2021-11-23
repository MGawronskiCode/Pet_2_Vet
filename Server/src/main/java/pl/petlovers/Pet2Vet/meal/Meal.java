package pl.petlovers.Pet2Vet.meal;

import lombok.*;

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


    public void modify(Meal meal) {
        setFood(meal.getFood());
        setFoodAmount(meal.getFoodAmount());
        setExpectedFeedingTime(meal.getExpectedFeedingTime());
        setActualFeedingTime(meal.getActualFeedingTime());
    }
}